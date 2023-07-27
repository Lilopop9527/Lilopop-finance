package com.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//import javax.annotation.PostConstruct;

@Configuration
public class GateConfig {
    @PostConstruct
    public void init(){
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            //自定义异常处理
            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue("服务器链接失败！"));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
