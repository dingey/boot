package com.d.base;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author d
 */
@SuppressWarnings("unused")
public class BaseController {
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
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
