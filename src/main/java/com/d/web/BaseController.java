package com.d.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.d.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author d
 */
public class BaseController {

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if (StringUtils.isEmpty(text)) {
					setValue(null);
				} else {
					try {
						setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(text));
					} catch (ParseException ex) {
						try {
							setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
						} catch (ParseException ex1) {
							setValue(null);
						}
					}
				}
			}

			@Override
			public String getAsText() {
				Date value = (Date) getValue();
				return ((value != null) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").format(value) : "");
			}
		});
	}

	HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	HttpServletResponse getResponse() {
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
