package com.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private static final String SECRET_KEY = "u9X2Kz6mB4vW7qN1xY3pE5sL8tJ0wQ2zM4vR6xY8bN1pQ3sT5vW7xZ9aC2eF4gH6";

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/users/register",
            "/api/users/login",
            "/api/farmers/register",
            "/api/farmers/login",
            "/api/runners/register",
            "/api/runners/login",
            "/api/admin/login"
    );

    private Key getSigninkey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        String path = exchange.getRequest().getURI().getPath();

        String method = exchange.getRequest().getMethod().toString();

        if (isPublicEndpoint(path)){
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);


        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigninkey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role",String.class);

            if (isAdminOnlyEndpoint(path)) {
                if (!role.equals("ADMIN")) {
                    exchange.getResponse()
                            .setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }

            if (isFarmerOnlyEndpoint(path, method)) {
                if (!role.equals("FARMER")) {
                    exchange.getResponse()
                            .setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }
            if (isUserOnlyEndpoint(path, method)) {
                if (!role.equals("CONSUMER")) {
                    exchange.getResponse()
                            .setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }

            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                    .header("X-User-Email",claims.getSubject())
                    .header("X-User-Role",claims.get("role",String.class))
                    .header("X-User-Id",claims.get("userId",Long.class).toString())
                    .build())
                    .build();

            return chain.filter(modifiedExchange);
        }catch (ExpiredJwtException e) {
            
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublicEndpoint(String path){
        return PUBLIC_ENDPOINTS.stream()
                .anyMatch(path::equals);
    }

    private boolean isAdminOnlyEndpoint(String path) {
        return path.startsWith("/api/admin/");
       
    }
    private boolean isFarmerOnlyEndpoint(
            String path, String method) {
        return path.equals("/api/products")
                && method.equals("POST");
    }
    private boolean isUserOnlyEndpoint(
            String path, String method) {
        return path.equals("/api/orders")
                && method.equals("POST");
    }


    @Override
    public int getOrder() {
        return -1;
    }
}

