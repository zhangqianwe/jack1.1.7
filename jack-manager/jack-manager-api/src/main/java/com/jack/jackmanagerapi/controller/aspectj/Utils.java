package com.jack.jackmanagerapi.controller.aspectj;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Auther: zhangqianwen
 * @Date: 2019-12-07 09:24
 * @Description:
 */
public class Utils {
    /**
     * 入参数据
     * @param joinPoint
     * @param request
     * @return
     */
    public static String preHandle(ProceedingJoinPoint joinPoint, HttpServletRequest request) {

        String reqParam = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        MethodInvocationProceedingJoinPoint signature1 = (MethodInvocationProceedingJoinPoint) signature;
        Object[] args = ((MethodInvocationProceedingJoinPoint) signature).getArgs();
//        arguments
//        execution(ResponseMessage com.jack.jackmanagerapi.controller.system.LoginController.login(User,HttpServletRequest))
//        String[] parameterNames = methodSignature.getParameterNames();
//        String parameterName = parameterNames[0];
        Method targetMethod = methodSignature.getMethod();
        Annotation[] annotations = targetMethod.getAnnotations();
        for (Annotation annotation : annotations) {
            //此处可以改成自定义的注解
            if (annotation.annotationType().equals(RequestMapping.class)) {
                reqParam = JSON.toJSONString(request.getParameterMap());
                break;
            }
        }
        return reqParam;
    }
}
