package com.d.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.d.util.JsonUtil;

@Component
@Aspect
@Profile({"test"})
public class CacheAspect {
	Logger logger = LoggerFactory.getLogger(CacheAspect.class);
	ConcurrentHashMap<String, Long> cacheMap = new ConcurrentHashMap<>();
	@Autowired
	private StringRedisTemplate srt;

	@PostConstruct
	public void init() {
		logger.info("自定义缓存初始化完毕。。。");
	}

	@Pointcut(value = "execution(* com.d.service..*.*(..))")
	public void around() {
	}

	@Around("around()")
	public <T> Object around(ProceedingJoinPoint pjp) throws Throwable {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		if (method.isAnnotationPresent(CacheMethod.class)) {
			CacheMethod cacheMethod = method.getAnnotation(CacheMethod.class);
			String key = spelKey(pjp);
			if (cacheMap.containsKey(key)) {
				if ((cacheMap.get(key) + cacheMethod.expire()) > System
						.currentTimeMillis()) {
					// 未过期
					String json = srt.opsForValue().get(key);
					return JsonUtil.singleton().fromJson(json,
							method.getReturnType());
				}
			}
			Object proceed = pjp.proceed();
			cacheMap.put(key, System.currentTimeMillis());
			srt.opsForValue().set(key, JsonUtil.singleton().toJson(proceed));
			return proceed;
		}
		return pjp.proceed();
	}

	String spelKey(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		CacheMethod cacheMethod = signature.getMethod()
				.getAnnotation(CacheMethod.class);
		String[] parameterNames = signature.getParameterNames();
		Object[] args = pjp.getArgs();
		if (cacheMethod.value().isEmpty() && cacheMethod.key().isEmpty()) {
			return signature.getMethod().getDeclaringClass().getName() + "."
					+ signature.getMethod().getName()
					+ JsonUtil.singleton().toJson(args);
		}
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				context.setVariable(parameterNames[i], args[i]);
			}
		}
		String value = parser.parseExpression(cacheMethod.key())
				.getValue(context, String.class);
		return value;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public static @interface CacheMethod {
		/* 缓存名称 */
		String value() default "";
		/* 缓存key值，支持spel表达式 */
		String key() default "";
		/* 过期时间60S */
		int expire() default 60000;
	}
}
