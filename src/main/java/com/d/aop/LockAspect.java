package com.d.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;

import com.d.util.AspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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

    @Pointcut(value = "execution(* com.d.service..*.*(..))")
    public void around() {
    }

    @Around("around()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        if (method.isAnnotationPresent(LockMethod.class) && condition(pjp)) {
            logger.debug("do lock.");
            LockMethod lockMethod = method.getAnnotation(LockMethod.class);
            String key = spelKey(pjp);
            Lock lock = registry.obtain(key);
            if (lockMethod.singleton()) {
                long now = System.currentTimeMillis();
                long last = now;
                if (lock.tryLock()) {
                    try {
                        try {
                            last = Long.valueOf(srt.opsForValue().get(key));
                        } catch (NumberFormatException e) {
                            last = 0;
                        }
                        if (last < now) {
                            srt.opsForValue().set(key, String.valueOf(now));
                        }
                    } finally {
                        lock.unlock();
                    }
                }
                if (last < now) {
                    logger.debug("执行单任务方法:【{}】", key);
                    return pjp.proceed();
                } else {
                    throw new RuntimeException("该方法同个时间只允许运行一个。");
                }
            } else {
                if (lock.tryLock()) {
                    logger.debug("执行锁方法:【{}】", key);
                    try {
                        return pjp.proceed();
                    } finally {
                        lock.unlock();
                    }
                }
            }
            throw new RuntimeException("服务器繁忙，请稍后再试。");
        }
        return pjp.proceed();
    }

    private boolean condition(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        LockMethod lockMethod = signature.getMethod().getAnnotation(LockMethod.class);
        return AspectUtil.spel(pjp, lockMethod.condition(), Boolean.class);
    }

    private String spelKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        LockMethod lockMethod = signature.getMethod().getAnnotation(LockMethod.class);
        if (lockMethod.value().isEmpty() && lockMethod.key().isEmpty()) {
            return signature.getMethod().getDeclaringClass().getName() + "." + signature.getMethod().getName();
        }
        String[] parameterNames = signature.getParameterNames();
        Object[] args = pjp.getArgs();
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return parser.parseExpression(lockMethod.key()).getValue(context, String.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface LockMethod {
        /* 是否单任务 */
        boolean singleton() default false;

        /* 锁的value值，支持spel表达式 */
        String value() default "";

        /* 锁的value值，支持spel表达式 */
        String key() default "";

        /* 锁的value值，支持spel表达式 */
        String condition() default "2>1";
    }
}
