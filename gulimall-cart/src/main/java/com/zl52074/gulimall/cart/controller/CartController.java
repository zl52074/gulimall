package com.zl52074.gulimall.cart.controller;

import com.zl52074.gulimall.cart.service.CartService;
import com.zl52074.gulimall.cart.vo.CartItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/28 16:45
 */
@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    /**
     * 获取当前用户的购物车商品项
     * @return
     */
    @GetMapping(value = "/currentUserCartItems")
    public List<CartItemVo> getCurrentCartItems() {

        List<CartItemVo> cartItemVoList = cartService.getUserCartItems();

        return cartItemVoList;
    }
}
