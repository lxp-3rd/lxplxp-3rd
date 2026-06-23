package com.ohgiraffers.lxp.auth.infrastructure.security;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtAuthenticationInterceptorTest {

    @Test
    void pre_handle_sets_authenticated_member_when_bearer_token_is_valid() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.LEARNER));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/members/me");
        request.addHeader("Authorization", "Bearer access-token");

        boolean result = interceptor.preHandle(request, new MockHttpServletResponse(), new Object());

        assertThat(result).isTrue();
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME))
                .isEqualTo(new AuthenticatedMember(1L, MemberRole.LEARNER));
    }

    @Test
    void pre_handle_allows_admin_when_admin_path_requires_admin_role() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.ADMIN));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/admin/members");
        request.addHeader("Authorization", "Bearer access-token");

        boolean result = interceptor.preHandle(request, new MockHttpServletResponse(), new Object());

        assertThat(result).isTrue();
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME))
                .isEqualTo(new AuthenticatedMember(1L, MemberRole.ADMIN));
    }

    @Test
    void pre_handle_fails_when_non_admin_accesses_admin_path() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.LEARNER));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/admin/members");
        request.addHeader("Authorization", "Bearer access-token");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FORBIDDEN);
    }

    @Test
    void pre_handle_fails_when_authorization_header_is_missing() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_REQUIRED);
    }

    @Test
    void pre_handle_fails_when_authorization_header_is_not_bearer() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic access-token");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_INVALID);
    }

    private enum TokenValidatePortStub implements TokenValidatePort {
        INSTANCE;

        @Override
        public AuthenticatedMember validateAccessToken(String token) {
            return new AuthenticatedMember(1L, MemberRole.LEARNER);
        }
    }
}
