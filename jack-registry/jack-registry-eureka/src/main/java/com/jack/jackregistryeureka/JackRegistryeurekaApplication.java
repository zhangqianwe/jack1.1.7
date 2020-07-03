package com.jack.jackregistryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JackRegistryeurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JackRegistryeurekaApplication.class, args);
    }

}
