package com.zl52074.gulimall.product.service.impl;

import com.zl52074.gulimall.product.entity.SpuInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.product.dao.SkuInfoDao;
import com.zl52074.gulimall.product.entity.SkuInfoEntity;
import com.zl52074.gulimall.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.save(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        String key = (String)params.get("key");
        String brandId  = (String)params.get("brandId");
        String catelogId  = (String)params.get("catelogId");
        String min  = (String)params.get("min");
        String max  = (String)params.get("max");

        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(key)){
            queryWrapper.and(obj->obj.eq("sku_id",key).or().like("sku_name",key));
        }
        if(StringUtils.isNotBlank(brandId)&&!"0".equalsIgnoreCase(brandId)){
            queryWrapper.eq("brand_id",brandId);
        }
        if(StringUtils.isNotBlank(catelogId)&&!"0".equalsIgnoreCase(brandId)){
            queryWrapper.eq("catalog_id",catelogId);
        }
        if(StringUtils.isNotBlank(min)){
            BigDecimal minBigDecimal = new BigDecimal(min);
            queryWrapper.ge("price",minBigDecimal);
        }
        if(StringUtils.isNotBlank(max)){
            BigDecimal maxBigDecimal = new BigDecimal(max);
            if(maxBigDecimal.compareTo(BigDecimal.ZERO)==1){
                queryWrapper.le("price",maxBigDecimal);
            }
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),queryWrapper
        );
        return new PageUtils(page);

    }

}
