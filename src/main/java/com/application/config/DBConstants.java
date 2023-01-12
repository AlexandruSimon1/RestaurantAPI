package com.application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//Connecting our REST API to database
@Data
@Component
@PropertySource("application-${spring.profiles.active}.properties")
public class DBConstants {
    @Value("${spring.datasource.driver}")
    private String db_driver;
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;
    @Value("${spring.datasource.database}")
    private String db_name;
    @Value("${server.port}")
    private String server_port;
}
