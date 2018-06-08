package com.d.config;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({ "dev", "test" })
public class Swagger2Config {
	Logger logger = LoggerFactory.getLogger(Swagger2Config.class);

	@PostConstruct
	public void initMethod() {
		logger.info("swagger2初始化成功。");
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class, HttpSession.class,
						Model.class)
				.apiInfo(apiInfo()).select()
				//.apis(RequestHandlerSelectors.basePackage("com.d.web"))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("接口列表").description("示例接口列表").contact(new Contact("d", null, null))
				.version("1.0").build();
	}
}
