package com.api_gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http
                .csrf(csrf->csrf.disable())
                .authorizeExchange(exchange ->exchange
                        .pathMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/farmers/register",
                                "/api/farmers/login",
                                "/api/runners/register",
                                "/api/runners/login",
                                "/api/admin/login"
                        ).permitAll()
                        .anyExchange().permitAll()
                );
        return http.build();
    }
}

