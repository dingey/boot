package com.d.aop;

import java.lang.reflect.Parameter;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.d.util.Result;

@Component
@Aspect
@Order(5)
public class ValidAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    
    @Pointcut(value = "execution(* com.d.web..*.*(..))")
    public void access() {
    }

    @Around("access()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        String[] parameterNames = methodSignature.getParameterNames();
        if (Objects.nonNull(parameterNames)) {
            logger.debug("do valid.");
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < parameterNames.length; i++) {
                Object value = pjp.getArgs()[i];
                if (value != null && value instanceof BindingResult) {
                    BindingResult r = (BindingResult) value;
                    if (r.getErrorCount() > 0) {
                        for (ObjectError e : r.getAllErrors()) {
                            if (e instanceof FieldError) {
                                FieldError fe = (FieldError) e;
                                s.append(fe.getField()).append(fe.getDefaultMessage()).append(";");
                            } else {
                                s.append(e.getCode()).append(e.getDefaultMessage()).append(";");
                            }
                        }
                        return Result.fail(s.toString());
                    }
                } else {
                    if (parameters[i].isAnnotationPresent(NotNull.class) && value == null) {
                        s.append(parameterNames[i]).append("不能为空；");
                    } else if ((parameters[i].isAnnotationPresent(NotEmpty.class) || parameters[i].isAnnotationPresent(NotBlank.class)) && (value == null || String.valueOf(value).isEmpty())) {
                        s.append(parameterNames[i]).append("不能为空字符；");
                    }
                }
            }
            if (s.length() > 0) {
                throw new IllegalArgumentException(s.toString());
            }
        }
        return pjp.proceed();
    }
}
