package com.ohgiraffers.lxp.auth.infrastructure.security;

import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMemberArgumentResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
                .addPathPatterns("/**"));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }
}
