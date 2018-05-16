/* 
 * Copyright (c) 2016, Jiehun.com.cn Inc. All Rights Reserved 
 */
package com.d.handler;

import javax.servlet.http.HttpServletRequest;
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

import com.d.util.Result;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Throwable.class)
	public Object defaultErrorHandler(Throwable e) {
		logger.error(e.getMessage(), e);
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = BindException.class)
	public Object bind(HttpServletRequest req, BindException bindException) throws Exception {
		StringBuilder errs = new StringBuilder();
		for (FieldError e : bindException.getFieldErrors()) {
			if (e.getDefaultMessage().indexOf("NumberFormatException") > 0) {
				errs.append(e.getRejectedValue() + "不是数字格式(" + e.getField() + ")");
			} else {
				errs.append(e.getRejectedValue() + "异常(" + e.getField() + ")");
			}
		}
		return Result.fail(errs.toString());
	}

	@ResponseBody
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public Object missingException(HttpServletRequest req, MissingServletRequestParameterException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail(e.getLocalizedMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public Object notReadableException(HttpServletRequest req, HttpMessageNotReadableException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail("请求参数不能为空");
	}

	@ResponseBody
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public Object methodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException e)
			throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail(e.getMethod() + "方法不支持");
	}

	@ResponseBody
	@ExceptionHandler(value = IllegalStateException.class)
	public Object illegalStateException(HttpServletRequest req, IllegalStateException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail(e.getLocalizedMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = UnavailableSecurityManagerException.class)
	public Object handler(HttpServletRequest req, UnavailableSecurityManagerException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail("未登录，请先登录");
	}

	@ResponseBody
	@ExceptionHandler(value = UnknownAccountException.class)
	public Object handler(HttpServletRequest req, UnknownAccountException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail("未知的用户名");
	}

	@ResponseBody
	@ExceptionHandler(value = IncorrectCredentialsException.class)
	public Object handler(HttpServletRequest req, IncorrectCredentialsException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail("密码错误");
	}

	@ResponseBody
	@ExceptionHandler(value = ExcessiveAttemptsException.class)
	public Object handler(HttpServletRequest req, ExcessiveAttemptsException e) throws Exception {
		logger.error(e.getMessage(), e);
		return Result.fail("错误登录过多");
	}
}
