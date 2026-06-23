package com.ohgiraffers.lxp.auth.infrastructure.security;

import com.ohgiraffers.lxp.auth.presentation.support.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthWebMvcConfig implements WebMvcConfigurer {

    private final JwtAuthenticationInterceptor jwtAuthenticationInterceptor;
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    public AuthWebMvcConfig(
            JwtAuthenticationInterceptor jwtAuthenticationInterceptor,
            LoginMemberArgumentResolver loginMemberArgumentResolver
    ) {
        this.jwtAuthenticationInterceptor = jwtAuthenticationInterceptor;
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns(AuthPathPolicy.PROTECTED_PATH_PATTERNS);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }
}
