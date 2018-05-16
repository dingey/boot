package com.d.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Aspect
public class LogAspect {
	@Pointcut(value = "execution(* com.d.web..*.*(..))")
	public void controller() {
	}

	@Before("controller()")
	public void controller(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		String requestPath = getRequestPath(signature.getMethod());

		String info = String.format("path:%s | %s", requestPath, getMethodInfo(point));
		System.out.println(info);
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
				String value = point.getArgs()[i] != null ? point.getArgs()[i].toString() : "null";
				sb.append(parameterNames[i] + ":" + value + "; ");
			}
		}
		sb = sb == null ? new StringBuilder() : sb;
		String info = String.format("class:%s | method:%s | args:%s", className, methodName, sb.toString());
		return info;
	}
}
