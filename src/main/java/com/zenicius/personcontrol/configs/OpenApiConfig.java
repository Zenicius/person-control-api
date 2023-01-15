package com.zenicius.personcontrol.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI personApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Person Control API").description("Simple Person control API"));
    }

}
