package com.admin_service.cofig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI adminServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FarmDirect - Admin Service API")
                        .description("Admin management and approval workflows")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FarmDirect Team")
                                .email("farmdirect@gmail.com")));
    }
}