package com.zl52074.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.coupon.entity.CouponSpuCategoryRelationEntity;

import java.util.Map;

/**
 * 优惠券分类关联
 *
 * @author zl52074
 * @email 
 * @date 2023-10-05 18:16:20
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

