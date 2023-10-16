package com.zl52074.gulimall.search.controller;

import com.sun.media.jfxmedia.logging.Logger;
import com.zl52074.gulimall.common.es.SkuEsModel;
import com.zl52074.gulimall.common.exception.BizCodeEnum;
import com.zl52074.gulimall.common.utils.R;
import com.zl52074.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/16 10:19
 */
@Slf4j
@RestController
@RequestMapping("search/save")
public class ElasticSaveController {
    @Autowired
    private ProductSaveService productSaveService;

    /**
     * 商品上架
     */
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        try{
            boolean flag = productSaveService.productStatusUp(skuEsModels);
            if(flag){
                log.error("商品上架保存索引异常");
                return R.error(BizCodeEnum.PDOUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PDOUCT_UP_EXCEPTION.getMsg());
            }
        }catch(Exception e){
            log.error("商品上架保存索引异常",e);
            e.printStackTrace();
            return R.error(BizCodeEnum.PDOUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PDOUCT_UP_EXCEPTION.getMsg());
        }
        return R.ok();
    }
}
