package com.ohgiraffers.lxp.auth.infrastructure.security;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMemberArgumentResolver;

@Configuration
public class AuthWebMvcConfig implements WebMvcConfigurer {

	private final ObjectProvider<TokenValidatePort> tokenValidatePortProvider;
	private final LoginMemberArgumentResolver loginMemberArgumentResolver;

	public AuthWebMvcConfig(
		ObjectProvider<TokenValidatePort> tokenValidatePortProvider,
		LoginMemberArgumentResolver loginMemberArgumentResolver
	) {
		this.tokenValidatePortProvider = tokenValidatePortProvider;
		this.loginMemberArgumentResolver = loginMemberArgumentResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		tokenValidatePortProvider.ifAvailable(tokenValidatePort -> registry
			.addInterceptor(new JwtAuthenticationInterceptor(tokenValidatePort))
			.addPathPatterns("/**")
			.excludePathPatterns("/h2-console", "/h2-console/**", "/images/**"));
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginMemberArgumentResolver);
	}
}
