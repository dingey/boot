package com.d.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.d.util.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static java.lang.String.*;

@Component
@Aspect
@Profile({"dev", "test"})
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut(value = "execution(* com.d.web..*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void controller(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String requestPath = getRequestPath(signature.getMethod());
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            logger.info("请求:{} | {}。", getSwagger(signature.getMethod()), getSwaggerMethodInfo(point));
        } else {
            logger.info("path:{} | {}", requestPath, getMethodInfo(point));
        }
    }

    private String getSwagger(Method method) {
        StringBuilder s = new StringBuilder();
        if (method.getDeclaringClass().isAnnotationPresent(Api.class)) {
            s.append("【").append(method.getDeclaringClass().getAnnotation(Api.class).tags()[0]).append("】-");
        }
        if (method.isAnnotationPresent(ApiOperation.class)) {
            s.append("【").append(method.getAnnotation(ApiOperation.class).value()).append("】");
        }
        return s.toString();
    }

    private String getSwaggerMethodInfo(JoinPoint point) {
        StringBuilder s = new StringBuilder("参数：");
        MethodSignature ms = (MethodSignature) point.getSignature();
        Parameter[] parameters = ms.getMethod().getParameters();
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        if (Objects.nonNull(parameterNames)) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (point.getArgs()[i] instanceof HttpServletRequest
                        || point.getArgs()[i] instanceof HttpServletResponse
                        || point.getArgs()[i] instanceof HttpSession || point.getArgs()[i] instanceof Model) {
                    continue;
                }
                String value = JsonUtil.build().toJson(point.getArgs()[i]);
                if (parameters[i].isAnnotationPresent(ApiParam.class)) {
                    s.append(parameterNames[i]).append(":").append(value).append("; ");
                } else {
                    s.append(parameterNames[i]).append(":").append(value).append("; ");
                }
            }
        }
        return s.toString();
    }

    private String getRequestPath(Method method) {
        for (Annotation anno : method.getAnnotations()) {
            if (anno.annotationType() == RequestMapping.class) {
                RequestMapping req = (RequestMapping) anno;
                return req.path().length > 0 ? req.path()[0] : req.value()[0];
            } else if (anno.annotationType() == GetMapping.class) {
                GetMapping req = (GetMapping) anno;
                return req.path().length > 0 ? req.path()[0] : req.value()[0];
            } else if (anno.annotationType() == PostMapping.class) {
                PostMapping req = (PostMapping) anno;
                return req.path().length > 0 ? req.path()[0] : req.value()[0];
            } else if (anno.annotationType() == PutMapping.class) {
                PutMapping req = (PutMapping) anno;
                return req.path().length > 0 ? req.path()[0] : req.value()[0];
            } else if (anno.annotationType() == DeleteMapping.class) {
                DeleteMapping req = (DeleteMapping) anno;
                return req.path().length > 0 ? req.path()[0] : req.value()[0];
            }
        }
        return null;
    }

    private String getMethodInfo(JoinPoint point) {
        String className = point.getSignature().getDeclaringType().getSimpleName();
        String methodName = point.getSignature().getName();
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        StringBuilder sb = null;
        if (Objects.nonNull(parameterNames)) {
            sb = new StringBuilder();
            for (int i = 0; i < parameterNames.length; i++) {
                if (point.getArgs()[i] instanceof HttpServletRequest
                        || point.getArgs()[i] instanceof HttpServletResponse
                        || point.getArgs()[i] instanceof HttpSession || point.getArgs()[i] instanceof Model) {
                    continue;
                }
                String value = JsonUtil.build().toJson(point.getArgs()[i]);
                sb.append(parameterNames[i]).append(":").append(value).append("; ");
            }
        }
        sb = sb == null ? new StringBuilder() : sb;
        return format("class:%s | method:%s | args{%s}", className, methodName, sb.toString());
    }
}
