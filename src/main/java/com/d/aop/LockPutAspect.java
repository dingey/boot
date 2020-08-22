package com.d.aop;

import com.d.annotation.LockPut;
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
public class LockPutAspect {
    private final Logger logger = LoggerFactory.getLogger(LockAspect.class);
    private final RedisConnectionFactory connectionFactory;
    private RedisLockRegistry registry;
    private final StringRedisTemplate srt;

    @Autowired
    public LockPutAspect(RedisConnectionFactory connectionFactory, StringRedisTemplate srt) {
        this.connectionFactory = connectionFactory;
        this.srt = srt;
    }

    @PostConstruct
    public void init() {
        registry = new RedisLockRegistry(connectionFactory, "lock_cache_key");
        logger.info("自定义标识锁初入口始化完毕。。。");
    }

    @Around("@annotation(com.d.annotation.LockPut)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        logger.debug("do lock put.");
        LockPut lockPut = method.getAnnotation(LockPut.class);
        String key = AspectUtil.spel(pjp, lockPut.key().isEmpty() ? lockPut.value() : lockPut.key(), String.class);
        Lock lock = registry.obtain(key);
        boolean exists = false;
        if (lock.tryLock()) {
            try {
                exists = srt.hasKey(key);
            } finally {
                lock.unlock();
            }
        }
        if (!exists) {
            srt.opsForValue().set(key, "", lockPut.timeout());
            return pjp.proceed();
        } else {
            throw new RuntimeException(AspectUtil.spel(pjp, lockPut.message(), String.class));
        }
    }
}
