package com.d.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

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
public class Swagger2Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class, HttpSession.class,
						Model.class)
				.apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.d.web"))
				.paths("prod".equals(System.getenv("spring.profiles.active")) ? PathSelectors.none()
						: PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("接口列表").description("示例接口列表").contact(new Contact("d", null, null))
				.version("1.0").build();
	}
}
