package com.zl52074.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.zl52074.gulimall.product.entity.AttrEntity;
import com.zl52074.gulimall.product.vo.AttrAttrGorupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteRelation(List<AttrAttrGorupRelationVo> relationVos);

    void saveBatchRelation(List<AttrAttrGorupRelationVo> relationVos);
}

