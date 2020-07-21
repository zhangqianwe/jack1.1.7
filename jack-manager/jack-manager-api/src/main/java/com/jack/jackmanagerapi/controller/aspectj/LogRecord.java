package com.jack.jackmanagerapi.controller.aspectj;


import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.*;

/**
 * 使用注解统一校验角色权限
 * @author zhanglf
 * @date 2019-04-29
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
}

