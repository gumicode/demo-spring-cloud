package com.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            System.out.println("Global filter baseMessage : " + config.getBaseMessage());
//
//            if(config.isPreLogger()) {
//                System.out.println("global Filter Start: request id : " + request.getId());
//            }
//
//            //custom post filter
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//
//                if(config.isPostLogger()) {
//                    System.out.println("global Filter end: request id : " + response.getStatusCode());
//                }
//            }));
//        };

        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            System.out.println("Logging filter baseMessage : " + config.getBaseMessage());

            if(config.isPreLogger()) {
                System.out.println("Logging Filter Start: request id : " + request.getId());
            }

            //custom post filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                if(config.isPostLogger()) {
                    System.out.println("Logging Filter end: request id : " + response.getStatusCode());
                }
            }));

        }, Ordered.LOWEST_PRECEDENCE);


        return filter;
    }


    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        public String getBaseMessage() {
            return baseMessage;
        }

        public boolean isPreLogger() {
            return preLogger;
        }

        public boolean isPostLogger() {
            return postLogger;
        }

        public void setBaseMessage(String baseMessage) {
            this.baseMessage = baseMessage;
        }

        public void setPreLogger(boolean preLogger) {
            this.preLogger = preLogger;
        }

        public void setPostLogger(boolean postLogger) {
            this.postLogger = postLogger;
        }
    }
}
