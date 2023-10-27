package com.zl52074.gulimall.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/28 1:31
 */
@Controller
public class OrderPageController {
    @GetMapping("/list.html")
    public String list(){
        return "list";
    }

    @GetMapping("/confirm.html")
    public String confirm(){
        return "confirm";
    }

    @GetMapping("/pay.html")
    public String pay(){
        return "pay";
    }

    @GetMapping("/detail.html")
    public String detail(){
        return "detail";
    }

}
