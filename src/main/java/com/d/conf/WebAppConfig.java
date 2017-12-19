package com.d.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.d.interceptor.ShiroInterceptor;

/**
 * @author di
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	/**
	 * 配置拦截器
	 * 
	 * @author lance
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new NullHandler()).addPathPatterns("/**");
		registry.addInterceptor(new ShiroInterceptor()).addPathPatterns("/**");
	}
}
