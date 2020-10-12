package com.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
//Swagger is used for online documentation in order to have the endpoints online and test all of them
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket automaticAPi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        Contact contact = new Contact("Alexandru Simon","", "asimon@ms3-inc.com");

        return new ApiInfo(
                "",
                "",
                "1.0.0",
                "",
                contact,
                "No License",
                "",
                Collections.emptyList());
    }
}
