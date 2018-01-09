/* 
 * Copyright (c) 2016, Jiehun.com.cn Inc. All Rights Reserved 
 */
package com.d.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			response.getWriter().write(e.getMessage());
		} catch (IOException ex) {
			e.printStackTrace();
		}
		e.printStackTrace();
		return mav;
	}

	@ResponseBody
	@ExceptionHandler(value = BindException.class)
	public Object bind(HttpServletRequest req, BindException bindException) throws Exception {
		List<String> errs = new ArrayList<>();
		for (FieldError e : bindException.getFieldErrors()) {
			if (e.getDefaultMessage().indexOf("NumberFormatException") > 0) {
				errs.add(e.getRejectedValue() + "不是数字格式(" + e.getField() + ")");
			} else {
				errs.add(e.getRejectedValue() + "异常(" + e.getField() + ")");
			}
		}
		return errs;
	}

	@ResponseBody
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public Object missingException(HttpServletRequest req, MissingServletRequestParameterException exception)
			throws Exception {
		return exception.getLocalizedMessage();
	}

	@ResponseBody
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public Object notReadableException(HttpServletRequest req, HttpMessageNotReadableException exception)
			throws Exception {
		return "请求参数不能为空";
	}

	@ResponseBody
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public Object methodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException exception)
			throws Exception {
		return exception.getMethod() + "方法不支持";
	}

	@ResponseBody
	@ExceptionHandler(value = IllegalStateException.class)
	public Object illegalStateException(HttpServletRequest req, IllegalStateException exception) throws Exception {
		return exception.getLocalizedMessage();
	}

	@ResponseBody
	@ExceptionHandler(value = UnavailableSecurityManagerException.class)
	public Object handler(HttpServletRequest req, UnavailableSecurityManagerException exception) throws Exception {
		return "未登录，请先登录";
	}

	@ResponseBody
	@ExceptionHandler(value = UnknownAccountException.class)
	public Object handler(HttpServletRequest req, UnknownAccountException exception) throws Exception {
		return "未知的用户名";
	}

	@ResponseBody
	@ExceptionHandler(value = IncorrectCredentialsException.class)
	public Object handler(HttpServletRequest req, IncorrectCredentialsException exception) throws Exception {
		return "密码错误";
	}

	@ResponseBody
	@ExceptionHandler(value = ExcessiveAttemptsException.class)
	public Object handler(HttpServletRequest req, ExcessiveAttemptsException exception) throws Exception {
		return "错误登录过多";
	}
}
