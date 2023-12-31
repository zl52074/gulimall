package com.zl52074.gulimall.product.dao;

import com.zl52074.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);

}
