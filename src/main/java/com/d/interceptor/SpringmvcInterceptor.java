package com.d.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author di
 */
public class SpringmvcInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod m = (HandlerMethod) handler;
		List<String> paths = new ArrayList<>();
		if (m.hasMethodAnnotation(RequestMapping.class)) {
			paths.addAll(Arrays.asList(m.getMethodAnnotation(RequestMapping.class).path()));
		}
		if (m.hasMethodAnnotation(GetMapping.class)) {
			paths.addAll(Arrays.asList(m.getMethodAnnotation(GetMapping.class).path()));
		}
		if (m.hasMethodAnnotation(PostMapping.class)) {
			paths.addAll(Arrays.asList(m.getMethodAnnotation(PostMapping.class).path()));
		}
		if (m.hasMethodAnnotation(PutMapping.class)) {
			paths.addAll(Arrays.asList(m.getMethodAnnotation(PutMapping.class).path()));
		}
		if (m.hasMethodAnnotation(DeleteMapping.class)) {
			paths.addAll(Arrays.asList(m.getMethodAnnotation(DeleteMapping.class).path()));
		}
		return paths.size() > 0;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
