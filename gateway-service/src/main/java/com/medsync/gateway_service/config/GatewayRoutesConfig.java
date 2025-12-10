package com.medsync.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // AUTH service (register, login, etc.)
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("http://auth-service:8081"))          // <--- service name, not localhost

                // PATIENT service
                .route("patient-service", r -> r
                        .path("/patients/**")
                        .uri("http://patient-service:8082"))

                // DOCTOR service
                .route("doctor-service", r -> r
                        .path("/doctors/**")
                        .uri("http://doctor-service:8083"))

                // APPOINTMENT service
                .route("appointment-service", r -> r
                        .path("/appointments/**")
                        .uri("http://appointment-service:8084"))

                // NOTIFICATION service
                .route("notification-service", r -> r
                        .path("/notifications/**")
                        .uri("http://notification-service:8085"))
                .build();
    }
}
