package com.zl52074.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.product.entity.AttrGroupEntity;
import com.zl52074.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.zl52074.gulimall.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params,Long catId);

    List<AttrGroupWithAttrsVo> getAttrGroupsWithAttrByCatelogId(Long catelogId);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

