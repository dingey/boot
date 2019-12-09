package com.d.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;

import com.d.annotation.LockMethod;
import com.d.util.AspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
@Profile({"dev", "test"})
public class LockAspect {
    private Logger logger = LoggerFactory.getLogger(LockAspect.class);
    private final RedisConnectionFactory connectionFactory;
    private RedisLockRegistry registry;
    private final StringRedisTemplate srt;

    @Autowired
    public LockAspect(RedisConnectionFactory connectionFactory, StringRedisTemplate srt) {
        this.connectionFactory = connectionFactory;
        this.srt = srt;
    }

    @PostConstruct
    public void init() {
        registry = new RedisLockRegistry(connectionFactory, "lock_key");
        logger.info("自定义redis锁初始化完毕。。。");
    }

    @Around("@annotation(com.d.annotation.LockMethod)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        if (condition(pjp)) {
            logger.debug("do lock.");
            LockMethod lockMethod = method.getAnnotation(LockMethod.class);
            String key = spelKey(pjp);
            Lock lock = registry.obtain(key);
            if (lockMethod.lockTime() > 0L) {
                long now = System.currentTimeMillis();
                long last = now;
                if (lock.tryLock(lockMethod.waitTime(), TimeUnit.MILLISECONDS)) {
                    try {
                        try {
                            last = Long.valueOf(srt.opsForValue().get(key));
                        } catch (NumberFormatException e) {
                            last = 0L;
                        }
                        if (last < now) {
                            srt.opsForValue().set(key, String.valueOf(now));
                        }
                    } finally {
                        lock.unlock();
                    }
                }
                if (last + lockMethod.lockTime() <= now) {
                    logger.debug("执行单任务方法:【{}】", key);
                    return pjp.proceed();
                } else {
                    if (lockMethod.message().isEmpty()) {
                        throw new RuntimeException("该方法同个时间只允许运行一个。");
                    } else {
                        throw new RuntimeException(AspectUtil.spel(pjp, lockMethod.message(), String.class));
                    }
                }
            } else {
                if (lock.tryLock(lockMethod.waitTime(), TimeUnit.MILLISECONDS)) {
                    logger.debug("执行锁方法:【{}】", key);
                    try {
                        return pjp.proceed();
                    } finally {
                        lock.unlock();
                    }
                }
                if (lockMethod.message().isEmpty()) {
                    throw new RuntimeException("服务器繁忙，请稍后再试。");
                } else {
                    throw new RuntimeException(AspectUtil.spel(pjp, lockMethod.message(), String.class));
                }
            }
        }
        return pjp.proceed();
    }

    private boolean condition(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        LockMethod lockMethod = signature.getMethod().getAnnotation(LockMethod.class);
        if (lockMethod.condition().isEmpty()) {
            return true;
        }
        return AspectUtil.spel(pjp, lockMethod.condition(), Boolean.class);
    }

    private String spelKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        LockMethod lockMethod = signature.getMethod().getAnnotation(LockMethod.class);
        if (lockMethod.key().isEmpty()) {
            return signature.getMethod().getDeclaringClass().getName() + "." + signature.getMethod().getName();
        }
        return AspectUtil.spel(pjp, lockMethod.key(), String.class);
    }
}
