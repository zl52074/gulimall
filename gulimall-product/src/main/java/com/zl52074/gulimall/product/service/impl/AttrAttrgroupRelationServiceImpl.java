package com.zl52074.gulimall.product.service.impl;

import com.zl52074.gulimall.product.entity.AttrEntity;
import com.zl52074.gulimall.product.service.AttrService;
import com.zl52074.gulimall.product.vo.AttrAttrGorupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.zl52074.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.zl52074.gulimall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteRelation(List<AttrAttrGorupRelationVo> relationVos) {
        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();
        relationVos.forEach(relation->{
            queryWrapper.or(obj->obj.eq("attr_id",relation.getAttrId()).eq("attr_group_id",relation.getAttrGroupId()));
        });
        this.remove(queryWrapper);
    }

    @Override
    public void saveBatchRelation(List<AttrAttrGorupRelationVo> relationVos) {
        List<AttrAttrgroupRelationEntity> relations = relationVos.stream().map(relationVo -> {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(relationVo, relation);
            return relation;
        }).collect(Collectors.toList());
        this.saveBatch(relations);

    }

}
