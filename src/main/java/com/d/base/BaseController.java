package com.d.base;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		cookie.setMaxAge(24 * 60 * 60);// 设置为24h
		cookie.setPath("/");
		getResponse().addCookie(cookie);
	}
	
}
