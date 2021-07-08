package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre filter
        //람다식의 객체로 exchanage와 chain객체를 받고, exchange로 부터 request와 response를 얻을수 있다.
        /*return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMesaage: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Global filter start: request id -> {}", request.getId());
            }
            //Custom Post Filter, Mono: 비동기 단일 값 전달시 사용
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                if (config.isPostLogger()) {
                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
                }
            }));
        };*/
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMesaage: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging PRE filter start: request id -> {}", request.getId());
            }
            //Custom Post Filter, Mono: 비동기 단일 값 전달시 사용
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                if (config.isPostLogger()) {
                    log.info("Logging POST Filter End: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE); // Loggin 우선순위 설정 HIGHEST_PRECEDENCE, LOWEST_PRECEDENCE 등을 이용

        return filter;
    }

    @Data
    public static class Config {
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

    }
}
