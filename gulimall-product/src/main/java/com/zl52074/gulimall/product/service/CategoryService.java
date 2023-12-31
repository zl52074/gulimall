package com.zl52074.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.product.entity.CategoryEntity;
import com.zl52074.gulimall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeCategoryByIds(List<Long> ids);

    Long[] findCatelogPath(Long catId);

    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getLevel1Categorys();

    Map<String,List<Catalog2Vo>> getCatalogJson();
}

