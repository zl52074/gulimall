package com.zl52074.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zl52074.gulimall.common.constatnt.ProductConstant.AttrEnum;
import com.zl52074.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.zl52074.gulimall.product.dao.AttrGroupDao;
import com.zl52074.gulimall.product.dao.CategoryDao;
import com.zl52074.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.zl52074.gulimall.product.entity.AttrGroupEntity;
import com.zl52074.gulimall.product.entity.CategoryEntity;
import com.zl52074.gulimall.product.service.CategoryService;
import com.zl52074.gulimall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.AttrDao;
import com.zl52074.gulimall.product.entity.AttrEntity;
import com.zl52074.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.save(attrEntity);
        if(attrEntity.getAttrType().equals(AttrEnum.ATTR_TYPE_BASE.getCode())){
            //保存分组
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            relation.setAttrGroupId(attrVo.getAttrGroupId());
            relation.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(relation);
        }
    }

    @Override
    public PageUtils queryAttrPage(Map<String, Object> params,Long catId,String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", type.toLowerCase().equals("base") ? AttrEnum.ATTR_TYPE_BASE.getCode() : AttrEnum.ATTR_TYPE_SALE.getCode());
        String key = (String) params.get("key");
        if(catId!=0){
            queryWrapper.eq("catelog_id",catId);
        }
        if(StringUtils.isNotBlank(key)){
            queryWrapper.and(obj->obj.eq("attr_id",key).or().like("attr_name",key));
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        //查询分类和分组名称
        List<AttrVo> AttrVoList = records.stream().map(attrEntity -> {
            AttrVo attrVo = new AttrVo();
            BeanUtils.copyProperties(attrEntity, attrVo);
            //查找分类
            Long catlogId = attrEntity.getCatelogId();
            if (catlogId != null) {
                CategoryEntity categoryEntity = categoryDao.selectById(catlogId);
                attrVo.setCatelogName(categoryEntity.getName());
            }
            if(type.toLowerCase().equals("base")){
                //查找分组
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao
                        .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if(relationEntity!=null&&relationEntity.getAttrGroupId()!=null){
                    Long attrGroupId = relationEntity.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    attrVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }
            return attrVo;
        }).collect(Collectors.toList());

        pageUtils.setList(AttrVoList);
        return pageUtils;
    }

    @Override
    public AttrVo getAttrInfo(Long attrId) {
        AttrVo attrVo = new AttrVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,attrVo);
        //查找分类
        if(attrEntity.getCatelogId()!=null){
            Long[] catelogPath = categoryService.findCatelogPath(attrEntity.getCatelogId());
            attrVo.setCatelogPath(catelogPath);
        }
        if(attrEntity.getAttrType().equals(AttrEnum.ATTR_TYPE_BASE.getCode())){
            //查找分组

            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao
                    .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if(relationEntity!=null&&relationEntity.getAttrGroupId()!=null){
                Long attrGroupId = relationEntity.getAttrGroupId();
                attrVo.setAttrGroupId(attrGroupId);
            }
        }

        return attrVo;
    }

    @Override
    @Transactional
    public void updateAttrById(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.updateById(attrEntity);
        if(attrEntity.getAttrType().equals(AttrEnum.ATTR_TYPE_BASE.getCode())){
            //修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            relationEntity.setAttrId(attrVo.getAttrId());
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attrVo.getAttrId()));
            //关联不存在新建关联
            if (count > 0) {
                attrAttrgroupRelationDao.update(relationEntity,
                        new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrVo.getAttrId()));
            } else {
                attrAttrgroupRelationDao.insert(relationEntity);
            }
        }
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long groupId) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", groupId));
        List<Long> attrIds = attrAttrgroupRelationEntity.stream().map(relation -> relation.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = this.listByIds(attrIds);
        return attrEntities;
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long groupId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type",AttrEnum.ATTR_TYPE_BASE.getCode());
        if(StringUtils.isNotBlank(key)){
            queryWrapper.and(obj->obj.eq("attr_id",key).or().like("attr_name",key));
        }
        //获取当前分组的分类id
        AttrGroupEntity attrGroup  = attrGroupDao.selectOne(new QueryWrapper<AttrGroupEntity>().eq("attr_group_id", groupId));
        Long catelogId = attrGroup.getCatelogId();
        if(catelogId!=null){
            //获取当前分类下的所有分组
            List<AttrGroupEntity> attrGroupList = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

            if(attrGroupList!=null&&attrGroupList.size()>0){
                List<Long> groupIds = attrGroupList.stream().map(group -> group.getAttrGroupId()).collect(Collectors.toList());
                List<AttrAttrgroupRelationEntity> relations = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
                //获取所有分组关联的所有属性
                List<Long> attrIds = relations.stream().map(relation -> relation.getAttrId()).collect(Collectors.toList());
                //查询所有属性排除关联了分组的属性
                if(attrIds!=null&&attrIds.size()>0){
                    queryWrapper.notIn("attr_id",attrIds);
                }
            }
            IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
            return new PageUtils(page);
        }
        return null;


    }

}
