package com.paradigmadigital.poc.reactive.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI(Environment environment) {
        return new OpenAPI()
                .info(new Info().title(environment.getProperty("info.app.name"))
                        .description(environment.getProperty("info.app.description"))
                        .version(environment.getProperty("info.app.version"))
                        .license(new License().name("MIT").url("/license.html")));
    }

}
