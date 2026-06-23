package com.ohgiraffers.lxp.auth.infrastructure.security;

import org.springframework.util.AntPathMatcher;

final class AuthPathPolicy {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    static final String[] PROTECTED_PATH_PATTERNS = {
            "/api/members/**",
            "/api/admin/**",
            "/api/auth/logout"
    };

    private static final String[] ADMIN_PATH_PATTERNS = {
            "/api/admin/**"
    };

    private AuthPathPolicy() {
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
