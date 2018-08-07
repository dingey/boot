package com.d.filter;

import com.d.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * URL过滤器
 *
 * @auther d
 */
public class UrlFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(UrlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("【URL过滤】{}请求路径：【{}】,GET查询参数【{}】,所有请求参数【{}】。", req.getMethod(), req.getRequestURI(), req.getQueryString(), JsonUtil.singleton().toJson(req.getParameterMap()));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
