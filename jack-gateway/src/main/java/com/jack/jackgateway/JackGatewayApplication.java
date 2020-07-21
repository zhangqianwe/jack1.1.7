package com.jack.jackgateway;

import com.jack.jackgateway.handler.JwtManageCheckGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Mono;


@SpringBootApplication
@EnableHystrix
public class JackGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(JackGatewayApplication.class, args);
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("192.168.0.203");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("123456");
        return jedisConnectionFactory;
    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
    @Bean
    public JwtManageCheckGatewayFilterFactory jwtCheckGatewayFilterFactory() {
        return new JwtManageCheckGatewayFilterFactory();
    }

}
