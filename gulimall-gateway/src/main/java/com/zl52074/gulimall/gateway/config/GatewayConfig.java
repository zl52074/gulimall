package com.zl52074.gulimall.gateway.config;


import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
public class GatewayConfig {

    @PostConstruct
    public void init()
    {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
               String exceptionClassName = t.getClass().getName();
                HashMap<String, String> map = new HashMap<>();
                if(exceptionClassName.equals("com.alibaba.csp.sentinel.slots.block.degrade.DegradeException")){
                   map.put("code", "500");
                   map.put("message", "服务降级！");
               }else if(exceptionClassName.equals("com.alibaba.csp.sentinel.slots.block.flow.FlowException")){
                   map.put("code", "500");
                   map.put("message", "服务流量过大!");
               }else if(exceptionClassName.equals("com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException")){
                    map.put("code", "500");
                    map.put("message", "服务流量过大!");
                } else{
                    map.put("code", "500");
                    map.put("message", exceptionClassName);
                }

                return ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(map));
            }
        };

        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
