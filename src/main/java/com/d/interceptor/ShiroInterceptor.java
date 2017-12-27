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

		//System.out.println("拦截到了mvc方法:" + handler2.getMethod() + "方法所用时间：" + time + "到" + new java.util.Date().getTime());
		if (isPermissioin) {
			// 有执行方法或权限不拦截
			return true;
		} else {
			// 跑出无权限异常
			throw new AuthorizationException();
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
