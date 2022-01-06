package com.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("application-${spring.profiles.active}.properties")
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${ui.url.origin}")
    private String myAllowedApi;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE").allowedOrigins(myAllowedApi);
    }
}
