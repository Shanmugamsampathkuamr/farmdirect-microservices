package com.user_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI userServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                .title("FarmDirect - User Service API")
                .description("User registration, login and management")
                .version("1.0.0")
                .contact(new Contact()
                        .name("FarmDirect Team")
                        .email("farmdirect@gmail.com")));
    }
}
