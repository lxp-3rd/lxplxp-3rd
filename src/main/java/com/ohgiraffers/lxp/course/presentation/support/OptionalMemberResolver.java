package com.ohgiraffers.lxp.course.presentation.support;

import org.springframework.stereotype.Component;

import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;

/**
 * 공개(비로그인 허용) 엔드포인트에서 Authorization 헤더를 optional하게 해석한다.
 * 유효한 Bearer 토큰이 있으면 memberId를, 없거나 유효하지 않으면 null을 반환한다(예외 없음).
 * 공유 인증 인터셉터를 수정하지 않고 auth의 기존 {@link TokenValidatePort}만 사용한다.
 */
@Component
public class OptionalMemberResolver {

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenValidatePort tokenValidatePort;

    public OptionalMemberResolver(TokenValidatePort tokenValidatePort) {
        this.tokenValidatePort = tokenValidatePort;
    }

    public Long resolveMemberId(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            return null;
        }
        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        if (token.isBlank()) {
            return null;
        }
        try {
            return tokenValidatePort.validateAccessToken(token).memberId();
        } catch (RuntimeException e) {
            return null;
        }
    }
}
