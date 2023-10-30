package com.zl52074.gulimall.order.web;

import com.zl52074.gulimall.common.exception.NoStockException;
import com.zl52074.gulimall.order.service.OrderService;
import com.zl52074.gulimall.order.vo.OrderConfirmVo;
import com.zl52074.gulimall.order.vo.OrderSubmitVo;
import com.zl52074.gulimall.order.vo.SubmitOrderResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /**
     * 下单功能
     * @param vo
     * @return
     */
    @PostMapping(value = "/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes) {

        try {
            SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
            //下单成功来到支付选择页
            //下单失败回到订单确认页重新确定订单信息
            if (responseVo.getCode() == 0) {
                //成功
                model.addAttribute("submitOrderResp",responseVo);
                return "pay";
            } else {
                String msg = "下单失败";
                switch (responseVo.getCode()) {
                    case 1: msg += "令牌订单信息过期，请刷新再次提交"; break;
                    case 2: msg += "订单商品价格发生变化，请确认后再次提交"; break;
                    case 3: msg += "库存锁定失败，商品库存不足"; break;
                }
                attributes.addFlashAttribute("msg",msg);
                return "redirect:http://order.gulimall.com/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NoStockException) {
                String message = ((NoStockException)e).getMessage();
                attributes.addFlashAttribute("msg",message);
            }else{
                e.printStackTrace();
                attributes.addFlashAttribute("msg",e.getMessage());
            }
            return "redirect:http://order.gulimall.com/toTrade";
        }
    }
}
