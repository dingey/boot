package com.d.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.d.util.UserToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author di
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod m = (HandlerMethod) handler;
        if (m.hasMethodAnnotation(RequiresUser.class) && request.getHeader("Authorization") != null) {
            String authorization = request.getHeader("Authorization");
            UserToken userToken = UserToken.fromTokenStringAES(authorization);
            if (userToken == null || !userToken.valid()) {
                throw new RuntimeException("授权失效");
            }
            request.setAttribute("userId", userToken.getId());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
