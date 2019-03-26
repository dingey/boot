package com.d.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import javax.annotation.PostConstruct;

import com.d.util.AspectUtil;
import com.di.kit.ClassUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.d.util.JsonUtil;

@Component
@Aspect
@Order(2)
@Profile({"dev", "test"})
public class CacheAspect {
    private final Logger logger = LoggerFactory.getLogger(CacheAspect.class);
    private final StringRedisTemplate srt;

    @Autowired
    public CacheAspect(StringRedisTemplate srt) {
        this.srt = srt;
    }

    @PostConstruct
    public void init() {
        logger.info("自定义缓存初始化完毕。。。");
    }

    @Around("@annotation(com.d.aop.CacheAspect.CacheMethod)||@annotation(com.d.aop.CacheAspect.CacheEvictMethod)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        if (method.isAnnotationPresent(CacheMethod.class)) {
            logger.debug("do cache.");
            CacheMethod cacheMethod = method.getAnnotation(CacheMethod.class);
            String key = spelKey(pjp);
            if (srt.hasKey(key)) {
                String json = srt.opsForValue().get(key);
                if (method.getReturnType().getTypeParameters().length == 0) {
                    return JsonUtil.singleton().fromJson(json, method.getReturnType());
                } else {
                    return JsonUtil.singleton().fromJsonWrapper(json, method.getReturnType(), ClassUtil.getMethodReturnGenericType(method));
                }
            }
            Object proceed = pjp.proceed();
            srt.opsForValue().set(key, JsonUtil.singleton().toJson(proceed), cacheMethod.expire());
            return proceed;
        } else if (method.isAnnotationPresent(CacheEvictMethod.class)) {
            String key = spelKey(pjp);
            srt.delete(key);
        }
        return pjp.proceed();
    }

    private String spelKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        CacheMethod cacheMethod = signature.getMethod().getAnnotation(CacheMethod.class);
        if (cacheMethod.value().isEmpty() && cacheMethod.key().isEmpty()) {
            return signature.getMethod().getDeclaringClass().getName() + "." + signature.getMethod().getName() + JsonUtil.singleton().toJson(pjp.getArgs());
        } else {
            return AspectUtil.spel(pjp, cacheMethod.key().isEmpty() ? cacheMethod.value() : cacheMethod.key(), String.class);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface CacheMethod {
        // 缓存名称
        String value() default "";

        // 缓存key值，支持spel表达式
        String key() default "";

        // 过期时间60S
        int expire() default 60000;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface CacheEvictMethod {
        // 缓存名称
        String value() default "";

        // 缓存key值，支持spel表达式
        String key() default "";
    }
}
