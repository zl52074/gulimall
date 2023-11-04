package com.zl52074.gulimall.seckill.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import com.zl52074.gulimall.common.exception.BizCodeEnum;
import com.zl52074.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局统一异常处理（自定义返回信息）
 */

@Slf4j
@Component
// 1、实现BlockExceptionHandler
public class MySentinelBlockExceptionHandler implements BlockExceptionHandler {


    // 2、重写handle方法
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {

        String msg = null;
        R response = null;
        if(e instanceof FlowException){
            msg="接口限流";
            response = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
        } else if(e instanceof DegradeException){
            msg="服务降级了";
        } else if(e instanceof ParamFlowException){
            msg="参数限流了";
        } else if(e instanceof AuthorityException){
            msg="权限规则不通过";
        } else if(e instanceof SystemBlockException){
            msg="系统保护";
        }
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(response == null ? msg : response));

    }
}
