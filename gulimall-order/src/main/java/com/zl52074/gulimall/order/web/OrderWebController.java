package com.zl52074.gulimall.order.web;

import com.zl52074.gulimall.order.service.OrderService;
import com.zl52074.gulimall.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/28 1:31
 */
@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

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


    /**
     * 去结算确认页
     */
    @GetMapping(value = "/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {

        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("confirmOrderData",confirmVo);
        //展示订单确认的数据

        return "confirm";
    }
}
