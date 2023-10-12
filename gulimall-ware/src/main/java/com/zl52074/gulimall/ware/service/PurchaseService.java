package com.zl52074.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.ware.entity.PurchaseEntity;
import com.zl52074.gulimall.ware.vo.MergeVo;
import com.zl52074.gulimall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 08:58:29
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceived(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void receive(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

