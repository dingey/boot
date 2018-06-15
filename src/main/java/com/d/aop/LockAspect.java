package com.d.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Profile({"test"})
public class LockAspect {
	Logger logger = LoggerFactory.getLogger(LockAspect.class);
	@Autowired
	private RedisConnectionFactory connectionFactory;
	private RedisLockRegistry registry;
	@Autowired
	private StringRedisTemplate srt;
	@PostConstruct
	public void init() {
		registry = new RedisLockRegistry(connectionFactory, "lock_key");
	}

	@Pointcut(value = "execution(* com.d.web..*.*(..))")
	public void around() {
	}

	@Around("around()")
	public <T> Object around(ProceedingJoinPoint pjp) throws Throwable {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		if (method.isAnnotationPresent(LockMethod.class)) {
			String key = method.getDeclaringClass().getName() + "."
					+ method.getName();
			Lock lock = registry.obtain(key);
			LockMethod lockMethod = method.getAnnotation(LockMethod.class);
			if (lockMethod.singleton()) {
				long now = System.currentTimeMillis() / 60000;
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
					logger.info("执行单任务方法:【{}】", key);
					return pjp.proceed();
				} else {
					return null;
				}
			} else {
				if (lock.tryLock()) {
					logger.info("执行锁方法:【{}】", key);
					try {
						return pjp.proceed();
					} finally {
						lock.unlock();
					}
				}
			}
		}
		return pjp.proceed();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public static @interface LockMethod {
		boolean singleton() default false;
	}
}