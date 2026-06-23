package com.ohgiraffers.lxp.auth.infrastructure.security;

final class AuthPathPolicy {

    static final String[] PROTECTED_PATH_PATTERNS = {
            "/api/members/**",
            "/api/admin/**",
            "/api/auth/logout"
    };

    private AuthPathPolicy() {
    }
}
