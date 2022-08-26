package com.paradigmadigital.poc.reactive.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class TraceLogFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        MDC.put("correlationId", UUID.randomUUID().toString());

        log.info("({}) {}", exchange.getRequest().getMethodValue(), exchange.getRequest().getURI());

        return chain.filter(exchange)
                .doOnTerminate(() -> log.info("{}", exchange.getResponse().getStatusCode()));
    }

}
