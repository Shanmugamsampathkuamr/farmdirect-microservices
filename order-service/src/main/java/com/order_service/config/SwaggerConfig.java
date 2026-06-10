package com.order_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI orderServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FarmDirect - Order Service API")
                        .description("Order placement, tracking and management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FarmDirect Team")
                                .email("farmdirect@gmail.com")));
    }
}
