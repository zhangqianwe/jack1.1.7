package com.jack.jackgateway;

import com.jack.jackgateway.handler.JwtManageCheckGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableHystrix
public class JackGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(JackGatewayApplication.class, args);
    }

    @Bean
    public JwtManageCheckGatewayFilterFactory jwtCheckGatewayFilterFactory() {
        return new JwtManageCheckGatewayFilterFactory();
    }

}
