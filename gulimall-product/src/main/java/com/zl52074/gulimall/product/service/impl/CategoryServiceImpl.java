package com.zl52074.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.CategoryDao;
import com.zl52074.gulimall.product.entity.CategoryEntity;
import com.zl52074.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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

}
