package com.zl52074.gulimall.product.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.AttrGroupDao;
import com.zl52074.gulimall.product.entity.AttrGroupEntity;
import com.zl52074.gulimall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long catId) {
        //select * from pms_attr_group where catelog_id = ? and attr_group_id= ? or attr_group_name like '%?%';
        String key = (String)params.get("key");
        IPage<AttrGroupEntity> page = null;
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        if(catId!=0){
            queryWrapper.eq("catelog_id",catId);
        }
        if(StringUtils.isNotBlank(key)){
            queryWrapper.and(obj->obj.eq("attr_group_id",key).or().like("attr_group_name",key));
        }
        page = this.page(new Query<AttrGroupEntity>().getPage(params),queryWrapper);
        return new PageUtils(page);
    }

}
