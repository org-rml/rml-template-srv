package com.rml.SERVICE_NAME.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO: Alterar título, descrição e versão do serviço.
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RML SRV - SERVICE_NAME") // TODO: Alterar
                        .description("API do serviço SERVICE_NAME") // TODO: Alterar
                        .version("1.0.0"));
    }
}
