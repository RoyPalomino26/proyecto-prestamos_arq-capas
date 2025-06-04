package dev.pitt.loans.loan_project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenApi(@Value("${springdoc.version}") String appVersion){
        return new OpenAPI()
                .info(new Info()
                        .title("Servicios de prestamos")
                        .version(appVersion)
                        .description("Servicios de gestión de prestamos")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
