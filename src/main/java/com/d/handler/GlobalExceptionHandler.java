/* 
 * Copyright (c) 2016, Jiehun.com.cn Inc. All Rights Reserved 
 */
package com.d.handler;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.d.util.Result;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Throwable.class)
	public Object handle(Throwable e) {
		logger.error(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = BindException.class)
	public Object handle(BindException bindException) {
		StringBuilder errs = new StringBuilder();
		for (FieldError e : bindException.getFieldErrors()) {
			if (e.getDefaultMessage().indexOf("NumberFormatException") > 0) {
				errs.append(e.getRejectedValue()).append("不是数字格式(").append(e.getField()).append(")");
			} else {
				errs.append(e.getRejectedValue()).append("异常(").append(e.getField()).append(")");
			}
		}
		return Result.fail(errs.toString());
	}

	@ResponseBody
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public Object handle(MissingServletRequestParameterException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public Object handle(HttpMessageNotReadableException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public Object handle(HttpRequestMethodNotSupportedException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail(e.getMethod() + "方法不支持");
	}

	@ResponseBody
	@ExceptionHandler(value = IllegalStateException.class)
	public Object handle(IllegalStateException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = IllegalArgumentException.class)
	public Object handle(IllegalArgumentException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public Object handle(MethodArgumentTypeMismatchException e) {
		logger.debug(e.getMessage(), e);
		Throwable cause = e.getRootCause();
		if (cause instanceof NumberFormatException) {
			return Result.fail(e.getName() + "值'" + e.getValue() + "'必须是数字类型.");
		}
		return Result.fail(e.getName() + " : " + e.getValue() + " " + e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = NumberFormatException.class)
	public Object handle(NumberFormatException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = UnavailableSecurityManagerException.class)
	public Object handler(UnavailableSecurityManagerException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail("未登录，请先登录");
	}

	@ResponseBody
	@ExceptionHandler(value = UnknownAccountException.class)
	public Object handler(UnknownAccountException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail("未知的用户名");
	}

	@ResponseBody
	@ExceptionHandler(value = IncorrectCredentialsException.class)
	public Object handler(IncorrectCredentialsException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail("密码错误");
	}

	@ResponseBody
	@ExceptionHandler(value = ExcessiveAttemptsException.class)
	public Object handler(ExcessiveAttemptsException e) {
		logger.debug(e.getMessage(), e);
		return Result.fail("错误登录过多");
	}
}
