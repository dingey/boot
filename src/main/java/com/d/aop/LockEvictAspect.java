package com.d.aop;

import com.d.annotation.LockEvict;
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

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

@Component
@Aspect
@Order(1)
@Profile({"dev", "test"})
public class LockEvictAspect {
    private Logger logger = LoggerFactory.getLogger(LockAspect.class);
    private final RedisConnectionFactory connectionFactory;
    private RedisLockRegistry registry;
    private final StringRedisTemplate srt;

    @Autowired
    public LockEvictAspect(RedisConnectionFactory connectionFactory, StringRedisTemplate srt) {
        this.connectionFactory = connectionFactory;
        this.srt = srt;
    }

    @PostConstruct
    public void init() {
        registry = new RedisLockRegistry(connectionFactory, "lock_cache_key");
        logger.info("自定义标识锁退出初始化完毕。。。");
    }

    @Around("@annotation(com.d.annotation.LockEvict)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        LockEvict lockEvict = method.getAnnotation(LockEvict.class);
        logger.debug("do lock evict.");
        String key = AspectUtil.spel(pjp, lockEvict.key().isEmpty() ? lockEvict.value() : lockEvict.key(), String.class);
        Lock lock = registry.obtain(key);
        if (lock.tryLock()) {
            try {
                srt.delete(key);
            } finally {
                lock.unlock();
            }
        }
        return pjp.proceed();
    }
}
