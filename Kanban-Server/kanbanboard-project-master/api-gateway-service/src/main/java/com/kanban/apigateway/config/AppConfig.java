package com.kanban.apigateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RouteLocator routeLocator (RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/v1/users/**")
                        .uri("lb://authentication-service"))
                .route(p -> p.path("/api/v1/kanbans/**","/api/v1/kanban-user/**")
                        .uri("lb://kanban-service"))
                .route(p -> p.path("/api/v1/user-profile/**")
                        .uri("lb://user-profile-service"))
                .route(p -> p.path("/api/v1/notifications/**")
                        .uri("lb://notification-service"))
                .build();
    }
}
