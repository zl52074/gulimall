package com.zl52074.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zl52074.gulimall.ware.vo.MergeVo;
import com.zl52074.gulimall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl52074.gulimall.ware.entity.PurchaseEntity;
import com.zl52074.gulimall.ware.service.PurchaseService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.R;



/**
 * 采购信息
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 08:58:29
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 完成采购单
     * @param doneVo
     * @return
     */
    @PostMapping("/done")
    public R finish(@RequestBody PurchaseDoneVo doneVo) {

        purchaseService.done(doneVo);

        return R.ok();
    }

    /**
     * 领取采购单
     * @param ids
     * @return
     */
    ///ware/purchase/receive
    @PostMapping("/receive")
    public R receive(@RequestBody List<Long> ids) {

        purchaseService.receive(ids);

        return R.ok();
    }

    //ware/purchase/merge
    @RequestMapping("/merge")
    public R marge(@RequestBody MergeVo MergeVo){
        purchaseService.mergePurchase(MergeVo);
        return R.ok();
    }

    ///ware/purchase/unreceive/list
    @RequestMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPageUnreceived(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody PurchaseEntity purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
