package com.application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//Connecting our REST API to data base
@Data
@Component
@PropertySource("application-${spring.profiles.active}.properties")
public class DBConstants {
    @Value("${db_driver}")
    private String db_driver;
    @Value("${db_url}")
    private String db_url;
    @Value("${db_username}")
    private String db_username;
    @Value("${db_password}")
    private String db_password;
    @Value("${db_name}")
    private String db_name;
    @Value("${server_port}")
    private String server_port;
}
