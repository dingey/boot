package com.d.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author d
 */
public class ShiroInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handler2 = (HandlerMethod) handler;
		RequiresPermissions checkPermission = handler2.getMethodAnnotation(RequiresPermissions.class);
		boolean isPermissioin = false;
		Subject currentUser = SecurityUtils.getSubject();
		// 没有获得注解 及不需要权限-- 则直接运行
		if (null != checkPermission) {
			String[] permission = checkPermission.value();
			for (String per : permission) {
				// 当前登录人 具有权限
				if (currentUser.isPermitted(per)) {
					isPermissioin = true;
					break;
				}
			}
		} else {
			isPermissioin = true;
		}

		if (isPermissioin) {
			return true;
		} else {
			throw new AuthorizationException("无访问权限");
		}
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
