package com.d.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.d.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author d
 */
public class BaseController {

	public HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}

	public Cookie getCookie(String name) {
		if (getRequest().getCookies() != null) {
			for (Cookie cookie : getRequest().getCookies()) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	public void addCookie(String name, Object value) {
		Cookie cookie = new Cookie(name.trim(), value.toString().trim());
		cookie.setMaxAge(24 * 60 * 60);// 设置为30min
		cookie.setPath("/");
		getResponse().addCookie(cookie);
	}

	public void editCookie(String name, Object value) {
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(value.toString());
					cookie.setPath("/");
					cookie.setMaxAge(24 * 30 * 60);// 设置为30min
					getResponse().addCookie(cookie);
					break;
				}
			}
		}
	}

	public User getUser() {
		Subject subject = SecurityUtils.getSubject();
		return (User) subject.getSession().getAttribute("user");
	}
}
