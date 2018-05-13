package com.d.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.d.util.Result;

@Component
@Aspect
public class ValidAspect {
	@Pointcut(value = "execution(* com.d.web..*.*(..))")
	public void access() {
	}

	@Around("access()")
	public <T> Object around(ProceedingJoinPoint pjp) throws Throwable {
		for (Object a : pjp.getArgs()) {
			if (a instanceof BindingResult) {
				BindingResult r = (BindingResult) a;
				if (r.getErrorCount() > 0) {
					StringBuilder s = new StringBuilder();
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
			}
		}
		return pjp.proceed();
	}
}
