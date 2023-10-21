package com.zl52074.gulimall.product.web;

import com.zl52074.gulimall.product.entity.CategoryEntity;
import com.zl52074.gulimall.product.service.SkuInfoService;
import com.zl52074.gulimall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/21 22:23
 */
@Controller
public class ItemController {
    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping(value = {"/{skuId}.html"})
    private String indexPage(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException{
        SkuItemVo vo = skuInfoService.item(skuId);
        model.addAttribute("item",vo);
        return "item";
    }
}
