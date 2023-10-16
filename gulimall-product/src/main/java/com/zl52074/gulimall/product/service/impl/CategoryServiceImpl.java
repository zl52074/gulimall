package com.zl52074.gulimall.product.service.impl;

import com.zl52074.gulimall.product.service.CategoryBrandRelationService;
import com.zl52074.gulimall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.CategoryDao;
import com.zl52074.gulimall.product.entity.CategoryEntity;
import com.zl52074.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类（可能会爆内存？）
        List<CategoryEntity> categoryList = baseMapper.selectList(null);
        //组装为tree
        List<CategoryEntity> categoryTree = categoryList.stream()
                //过滤parentCid=0的根数据
                .filter(category -> category.getParentCid() == 0)
                //挨个装填子分类
                .map(category -> {
                    category.setChildren(getChildren(category, categoryList));
                    return category;
                })
                //根据sort字段排序
                .sorted(Comparator.comparingInt(category -> (category.getSort() == null ? 0 : category.getSort())))
                //收集为list
                .collect(Collectors.toList());
        return categoryTree;

    }


    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> categoryList) {
        List<CategoryEntity> children =  categoryList.stream()
                .filter(category -> category.getParentCid().equals(root.getCatId()))
                .map(category -> {
                    category.setChildren(getChildren(category, categoryList));
                    return category;
                })
                .sorted(Comparator.comparingInt(category -> (category.getSort() == null ? 0 : category.getSort())))
                .collect(Collectors.toList());
        return children;
    }

    @Override
    public void removeCategoryByIds(List<Long> ids) {
        //TODO 刪除前检查引用
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public Long[] findCatelogPath(Long catId) {
        List<Long> path = new ArrayList<>();
        getParentId(catId,path);
        Collections.reverse(path);
        return path.toArray(new Long[path.size()]);
    }


    private void getParentId(Long catId,List<Long> path){
        path.add(catId);
        CategoryEntity category = this.getById(catId);
        if(category.getParentCid()!=0){
            getParentId(category.getParentCid(),path);
        }

    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return this.list(new QueryWrapper<CategoryEntity>().eq("cat_level",1));
    }

    @Override
    public Map<String,List<Catalog2Vo>> getCatalogJson(){
        //查询全部1级分类
        List<CategoryEntity> level1Categories = this.list(new QueryWrapper<CategoryEntity>().eq("cat_level",1));
        List<CategoryEntity> level2Categories = this.list(new QueryWrapper<CategoryEntity>().eq("cat_level",2));
        List<CategoryEntity> level3Categories = this.list(new QueryWrapper<CategoryEntity>().eq("cat_level",3));

        List<Catalog2Vo> catalog2VoList = level2Categories.stream().map(level2 -> {
            Catalog2Vo catalog2Vo = new Catalog2Vo();
            catalog2Vo.setId(level2.getCatId().toString());
            catalog2Vo.setName(level2.getName());
            catalog2Vo.setCatalog1Id(level2.getParentCid().toString());
            List<Catalog2Vo.Catalog3Vo> level3VoList = level3Categories.stream().filter(level3 -> {
                return level3.getParentCid().equals(level2.getCatId());
            }).map(level3 -> {
                Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo();
                catalog3Vo.setCatalog2Id(level2.getCatId().toString());
                catalog3Vo.setName(level3.getName());
                catalog3Vo.setId(level3.getCatId().toString());
                return catalog3Vo;
            }).collect(Collectors.toList());
            catalog2Vo.setCatalog3List(level3VoList);
            return catalog2Vo;
        }).collect(Collectors.toList());
        int i = 0 ;
        Map<String, List<Catalog2Vo>> map = level1Categories.stream().collect(Collectors.toMap(level1->level1.getCatId().toString(), level1 -> {
            return catalog2VoList.stream().filter(catalog2Vo -> catalog2Vo.getCatalog1Id().equals(level1.getCatId().toString())).collect(Collectors.toList());
        }));

        return map;
    }
}
