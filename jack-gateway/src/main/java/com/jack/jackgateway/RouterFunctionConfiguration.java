package com.jack.jackgateway;

import com.jack.jackgateway.handler.HystrixFallbackHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @Author czx
 * @Description //TODO 配置路由及拦截器
 * 动态路由 blog https://blog.csdn.net/zhuyu19911016520/article/details/86557165
 * @Date 16:22 2019/4/16
 **/

/**
 * RouterFunction 创建路由
 */
@Configuration
@AllArgsConstructor
public class RouterFunctionConfiguration {

    private final HystrixFallbackHandler hystrixFallbackHandler;

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.path("/fallback")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hystrixFallbackHandler);
    }
}
