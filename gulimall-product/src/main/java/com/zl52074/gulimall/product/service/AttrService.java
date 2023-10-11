package com.zl52074.gulimall.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.zl52074.gulimall.product.entity.AttrEntity;
import com.zl52074.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attrVo);

    PageUtils queryAttrPage(Map<String, Object> params,Long catelogId,String type);

    AttrVo getAttrInfo(Long attrId);

    void updateAttrById(AttrVo attrVo);


    List<AttrEntity> getRelationAttr(Long groupId);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long groupId);
}

