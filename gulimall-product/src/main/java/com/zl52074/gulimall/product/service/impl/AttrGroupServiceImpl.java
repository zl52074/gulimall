package com.zl52074.gulimall.product.service.impl;

import com.zl52074.gulimall.product.entity.AttrEntity;
import com.zl52074.gulimall.product.service.AttrService;
import com.zl52074.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.AttrGroupDao;
import com.zl52074.gulimall.product.entity.AttrGroupEntity;
import com.zl52074.gulimall.product.service.AttrGroupService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;
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

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupsWithAttrByCatelogId(Long catelogId) {
        //根据分类查找分组
        List<AttrGroupEntity> groups = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //查找分组下的属性
        List<AttrGroupWithAttrsVo> vos = groups.stream().map(group -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, vo);
            List<AttrEntity> attrs = attrService.getRelationAttr(group.getAttrGroupId());
            vo.setAttrs(attrs);
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }

}
