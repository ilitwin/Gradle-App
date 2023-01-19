package com.app.core.module;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableMongoRepositories(basePackages = "com.app.core.api.dao")
@ConditionalOnProperty(name = "com.app.core.api.enabled", havingValue = "true")
@ComponentScan(basePackages = "com.app.core.api")
public class ApiModuleConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("App Core API")
                                .version(appVersion)
                                .description(
                                        "This is a sample App Core Api documentation - a library for OpenAPI 3 with spring boot.")
                                .termsOfService("http://swagger.io/terms/")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("http://springdoc.org")));
    }
}
