package com.jack.jackmanagerapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jack.mapper")
public class JackManagerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JackManagerApiApplication.class, args);
    }

}
