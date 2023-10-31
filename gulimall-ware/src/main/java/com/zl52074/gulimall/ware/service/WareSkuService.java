package com.zl52074.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.to.OrderTo;
import com.zl52074.gulimall.common.to.mq.StockLockedTo;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.vo.SkuHasStockVo;
import com.zl52074.gulimall.ware.entity.WareSkuEntity;
import com.zl52074.gulimall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 08:58:29
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    boolean orderLockStock(WareSkuLockVo vo);

    /**
     * 解锁库存
     * @param to
     */
    void unlockStock(StockLockedTo to);

    /**
     * 解锁订单
     * @param orderTo
     */
    void unlockStock(OrderTo orderTo);
}

