package com.zl52074.gulimall.seckill.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.zl52074.gulimall.common.utils.R;
import com.zl52074.gulimall.seckill.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/11/5 0:26
 */
@RestController
public class SentinelDemoController {
    @Autowired
    private ProductFeignService productFeignService;
    @GetMapping("/hello")
    //自定义sentinel资源，设置异常处理方法
    @SentinelResource(value = "hello1",blockHandler = "blockHandlerMethod")
    public R hello(){
        return R.ok().setData("hello");
    }
    /**
     * 1.处理方法必须与受保护资源在同一个类中
     * 2.处理方法的修饰符必须为pulbic
     * 3.处理方法的参数列表必须和受保护资源相同，并在最后加上BlockException e接收异常
     * 4.处理方法的返回值必须和受保护资源相同
     * 如果处理方法在其他类，需要指定blockHandlerClass
     * @SentinelResource(value = "hello",blockHandlerClass ={MyBlockExceptionHandler.class},blockHandler = "blockExceptionHandler")
     *
     */
    public R blockHandlerMethod(BlockException e){
        e.printStackTrace();
        String msg = "自定义blockHandlerMethod";
        if(e instanceof FlowException){
            msg="自定义blockHandlerMethod：限流";
        }
        return R.error().setData(msg);
    }


    /**
     * 注意fallback和blockHandler的区别
     * 1. blockHandler是专门(只)处理sentinel流控降级规则抛出的BlockException
     * 2. 而fallback默认处理所有的异常，其中包括BlockEcxeption（因为BlockException是Exception的子类），也就是说如果我们不配置blockHandler，其抛出BlockEcxeption也将会进入fallback方法中
     * 3. fallback处理的异常可通过@SentinelResource中的exceptionsToIgnore属性排除（不知道为什么我测试可以排除ArithmeticException，但是排除不了BlockException）
     * 4. 如果同时配置了blockHandler和fallback，出现BlockException时将进入BlockHandler方法中处理
     */
    @GetMapping("/hello/{num}")
    @SentinelResource(value = "hello2",blockHandler = "blockHandlerMethod",fallback = "fallbackMethod")
    public R hello(@PathVariable("num") Integer num){
        int result = 100/num;
        return R.ok().setData(result);
    }

    /**
     * 1.方法必须和被保护资源处于同一个类
     * 2.方法参数列表和受保护资源一致（可选是否在最后添加一个Throwable类型参数接收异常，注意类型必须是Throwable）
     * 3.方法返回值必须和受保护资源相同
     * 如果处理方法在其他类，需要指定fallbackClass
     * @SentinelResource(value = "hello",fallbackClass = {MyFallbackHandler.class},fallback = "fallbackMethod")
     */
    public R fallbackMethod (Integer num,Throwable e){
        return R.ok().setData("操作失败，出现"+e.getClass().getSimpleName()+"异常，进入指定的fallback方法");
    }


    @GetMapping("/product")
    public R product(){
        R skuInfo = productFeignService.getSkuInfo(1L);
        return skuInfo;
    }

}
