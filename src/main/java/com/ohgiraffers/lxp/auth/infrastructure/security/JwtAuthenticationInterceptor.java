package com.ohgiraffers.lxp.auth.infrastructure.security;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenValidatePort tokenValidatePort;

    public JwtAuthenticationInterceptor(TokenValidatePort tokenValidatePort) {
        this.tokenValidatePort = tokenValidatePort;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = extractBearerToken(request);
        AuthenticatedMember authenticatedMember = tokenValidatePort.validateAccessToken(token);
        validateAuthorization(request, authenticatedMember);
        request.setAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, authenticatedMember);
        return true;
    }

    private void validateAuthorization(HttpServletRequest request, AuthenticatedMember authenticatedMember) {
        if (AuthPathPolicy.isAdminPath(request.getRequestURI()) && authenticatedMember.role() != MemberRole.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
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
