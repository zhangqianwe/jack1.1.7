package com.jack.jackmanagerapi.controller.aspectj;


import com.alibaba.fastjson.JSON;
import com.jack.jackmanagerapi.controller.system.RequestWrapper;
import com.jack.pojo.User;
import com.jack.utils.GetRequestJsonUtils;
import com.jack.utils.NetworkUtil;
import com.jack.utils.RequestReadUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 角色权限校验-AOP
 *
 * @author zhanglf
 * @date 2019-04-29
 */
@Aspect
@Component
@Slf4j
public class LogRecordAspect {
    private static final AtomicInteger onlineNumber = new AtomicInteger(0);
    private static final String START_TIME = "request-start";
    private static final String REQUEST_CLASS_NAME = "request-class-name";
    private static final String REQUEST_METHOD_NAME = "request-method-name";

    //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型
    @Pointcut("@annotation(com.jack.jackmanagerapi.controller.aspectj.LogRecord)")
    private void permissionCheckCut() {
    }

    ;

    //定义了切面的处理逻辑。即方法上加了@PermissionCheck
    @Around("permissionCheckCut()")
    public synchronized Object around(ProceedingJoinPoint pjp) throws Throwable {
        //1.记录日志信息
        Signature signature = pjp.getSignature();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        log.info("className:{},methodName:{}", className, methodName);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
//        log.info("【请求 URL】：{}", request.getRequestURL());
//        log.info("【请求 IP】：{}", request.getRemoteAddr());

//        String reqParam = Utils.preHandle(pjp,request);
//        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
//        String body = HttpHelper.getBodyString(requestWrapper);
//        System.out.println(body);
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        JSONObject jsonObject= JSONObject.fromObject(parameterMap);
//        String requestJsonString = GetRequestJsonUtils.getRequestJsonString(request);
        String authType = request.getAuthType();

//        String xmlString = getParamByStream(request);
//        log.info("【请求参数】：{}，", reqParam);
//        RequestWrapper requestWrapper = new RequestWrapper(request);
//        String body = requestWrapper.getBody();
//        System.out.println("body： "+body);
        Long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        request.setAttribute(REQUEST_CLASS_NAME,pjp.getSignature().getDeclaringTypeName());
        request.setAttribute(REQUEST_METHOD_NAME,pjp.getSignature().getName());
        Object proceed = pjp.proceed();
        return proceed;
    }

    @After("permissionCheckCut()")
    public void after() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        String requestClassName = (String)request.getAttribute(REQUEST_CLASS_NAME);
        String requestMethodName = (String)request.getAttribute(REQUEST_METHOD_NAME);
        log.info("【请求类名】：{}，【请求方法名】：{}", requestClassName, requestMethodName);
        log.info("【请求 URL】：{}", request.getRequestURL());
        log.info("【请求 IP】：{}", NetworkUtil.getIpAddress(request));
        Long start = (Long) request.getAttribute(START_TIME);
        Long end = System.currentTimeMillis();
        log.info("【请求耗时】：{}毫秒", end - start);
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}", userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
    }
    @AfterReturning(returning = "obj", pointcut = "permissionCheckCut()")
    public void doAfterReturning(Object obj) throws Throwable {
        //处理完请求，返回内容
        log.info("处理完请求，返回内容 : " + JSON.toJSONString(obj));
    }


}

