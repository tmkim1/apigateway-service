package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre filter
        //람다식의 객체로 exchanage와 chain객체를 받고, exchange로 부터 request와 response를 얻을수 있다.
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Custom Pre Filter: request id -> {}", request.getId());

            //Custom Post Filter, Mono: 비동기 단일 값 전달시 사용
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                log.info("Custom POST Filter: response code -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {
        //Put the configuration properties

    }
}
