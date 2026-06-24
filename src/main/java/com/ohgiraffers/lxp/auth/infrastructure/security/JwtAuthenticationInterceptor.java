package com.ohgiraffers.lxp.auth.infrastructure.security;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.auth.presentation.support.RequireRole;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenValidatePort tokenValidatePort;

    public JwtAuthenticationInterceptor(TokenValidatePort tokenValidatePort) {
        this.tokenValidatePort = tokenValidatePort;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        List<RequireRole> requireRoles = extractRequireRoles(handler);
        String requestPath = requestPathWithoutContextPath(request);
        boolean pathRequiresAuth = AuthPathPolicy.requiresAuth(requestPath);

        if (!pathRequiresAuth && requireRoles.isEmpty()) {
            return true;
        }

        String token = extractBearerToken(request);
        AuthenticatedMember authenticatedMember = tokenValidatePort.validateAccessToken(token);

        if (AuthPathPolicy.isAdminPath(requestPath) && authenticatedMember.role() != MemberRole.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        if (!hasRequiredRoles(requireRoles, authenticatedMember.role())) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        request.setAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, authenticatedMember);
        return true;
    }

    private List<RequireRole> extractRequireRoles(Object handler) {
        if (!(handler instanceof HandlerMethod hm)) {
            return List.of();
        }
        RequireRole classAnnotation = hm.getBeanType().getAnnotation(RequireRole.class);
        RequireRole methodAnnotation = hm.getMethodAnnotation(RequireRole.class);

        if (classAnnotation != null && methodAnnotation != null) {
            return List.of(classAnnotation, methodAnnotation);
        }
        if (classAnnotation != null) {
            return List.of(classAnnotation);
        }
        if (methodAnnotation != null) {
            return List.of(methodAnnotation);
        }
        return List.of();
    }

    private boolean hasRequiredRoles(List<RequireRole> requireRoles, MemberRole role) {
        return requireRoles.stream()
                .allMatch(requireRole -> Arrays.asList(requireRole.value()).contains(role));
    }

    private String requestPathWithoutContextPath(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath == null || contextPath.isBlank() || !requestUri.startsWith(contextPath)) {
            return requestUri;
        }
        String path = requestUri.substring(contextPath.length());
        return path.isBlank() ? "/" : path;
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new BusinessException(ErrorCode.TOKEN_REQUIRED);
        }
        if (!authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        if (token.isBlank()) {
            throw new BusinessException(ErrorCode.TOKEN_REQUIRED);
        }
        return token;
    }
}
