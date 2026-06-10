package com.media_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI mediaServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("FarmDirect - Media Service API")
                        .description("Media upload and management via cloudinary")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Farmer Team")
                                .email("farmdirect@gmail.com"))
                );
    }
}
