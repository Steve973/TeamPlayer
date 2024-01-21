package org.example.spring.rest;

import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DataInitRouter {

    private static final String INIT_PATH = "/init";

    @Bean
    public RouterFunction<ServerResponse> dataInitRoute(DataInitHandler handler) {
        return SpringdocRouteBuilder.route()
                .PUT(INIT_PATH, handler::init, ops -> ops.operationId("initData"))
                .build();
    }
}
