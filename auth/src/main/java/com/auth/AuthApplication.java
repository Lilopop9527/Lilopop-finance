package com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
@SpringBootApplication
@EnableDiscoveryClient
//@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
