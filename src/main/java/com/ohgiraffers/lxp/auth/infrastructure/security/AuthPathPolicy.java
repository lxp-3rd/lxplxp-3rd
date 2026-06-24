package com.ohgiraffers.lxp.auth.infrastructure.security;

import org.springframework.util.AntPathMatcher;

final class AuthPathPolicy {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final String[] PROTECTED_PATH_PATTERNS = {
            "/api/members/**",
            "/api/admin/**",
            "/api/auth/logout",
    };

    private static final String[] ADMIN_PATH_PATTERNS = {
            "/api/admin/**"
    };

    // 로그인 상태에서만 접근 가능한 로드맵 개인화 GET 엔드포인트
    private static final String[] ROADMAP_PERSONAL_PATTERNS = {
            "/api/roadmaps/available",
            "/api/roadmaps/participating",
            "/api/roadmaps/created",
    };

    private AuthPathPolicy() {
    }

    static boolean requiresAuth(String method, String requestUri) {
        for (String pattern : PROTECTED_PATH_PATTERNS) {
            if (PATH_MATCHER.match(pattern, requestUri)) {
                return true;
            }
        }
        // 로드맵 쓰기 작업(POST/PUT/DELETE)은 항상 인증 필요
        if (requestUri.startsWith("/api/roadmaps") && !method.equalsIgnoreCase("GET")) {
            return true;
        }
        // 로드맵 개인화 GET 엔드포인트는 인증 필요
        if (method.equalsIgnoreCase("GET")) {
            for (String pattern : ROADMAP_PERSONAL_PATTERNS) {
                if (PATH_MATCHER.match(pattern, requestUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isAdminPath(String requestUri) {
        for (String pattern : ADMIN_PATH_PATTERNS) {
            if (PATH_MATCHER.match(pattern, requestUri)) {
                return true;
            }
        }
        return false;
    }
}
