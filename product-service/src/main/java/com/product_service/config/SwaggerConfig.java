package com.product_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI productServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FarmDirect - Product Service API")
                        .description("Crop product management and expiry tracking")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FarmDirect Team")
                                .email("farmdirect@gmail.com")));
    }
}