package com.ohgiraffers.lxp.auth.infrastructure.security;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.auth.presentation.support.RequireRole;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtAuthenticationInterceptorTest {

    // ─── 기존: 경로 기반 인증 ─────────────────────────────────────────────

    @Test
    @DisplayName("보호 경로에서 유효한 토큰이면 AuthenticatedMember를 request에 저장한다")
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
    @DisplayName("admin 경로에서 ADMIN 역할이면 통과한다")
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
    @DisplayName("admin 경로에서 ADMIN 이외 역할이면 FORBIDDEN 예외가 발생한다")
    void pre_handle_fails_when_non_admin_accesses_admin_path() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.LEARNER));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/admin/members");
        request.addHeader("Authorization", "Bearer access-token");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.FORBIDDEN.getMessage());
    }

    @Test
    @DisplayName("보호 경로에서 Authorization 헤더가 없으면 TOKEN_REQUIRED 예외가 발생한다")
    void pre_handle_fails_when_authorization_header_is_missing() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/members/me");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("context path가 있어도 보호 경로 인증이 적용된다")
    void pre_handle_requires_token_when_protected_path_has_context_path() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/lxp");
        request.setRequestURI("/lxp/api/members/me");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("보호 경로에서 Bearer 방식이 아닌 토큰이면 TOKEN_INVALID 예외가 발생한다")
    void pre_handle_fails_when_authorization_header_is_not_bearer() {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/members/me");
        request.addHeader("Authorization", "Basic access-token");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_INVALID.getMessage());
    }

    // ─── 신규: @RequireRole 어노테이션 기반 인증 ──────────────────────────

    @Test
    @DisplayName("@RequireRole 역할이 일치하면 통과한다")
    void pre_handle_allows_when_require_role_matches() throws Exception {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.INSTRUCTOR));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/some-endpoint");
        request.addHeader("Authorization", "Bearer token");

        boolean result = interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod("instructorOnly"));

        assertThat(result).isTrue();
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME))
                .isEqualTo(new AuthenticatedMember(1L, MemberRole.INSTRUCTOR));
    }

    @Test
    @DisplayName("@RequireRole 역할이 일치하지 않으면 FORBIDDEN 예외가 발생한다")
    void pre_handle_fails_when_require_role_does_not_match() throws Exception {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.LEARNER));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/some-endpoint");
        request.addHeader("Authorization", "Bearer token");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod("instructorOnly")))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.FORBIDDEN.getMessage());
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME)).isNull();
    }

    @Test
    @DisplayName("클래스와 메서드 @RequireRole이 모두 있으면 둘 다 만족해야 한다")
    void pre_handle_fails_when_method_role_does_not_satisfy_class_role() throws Exception {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.LEARNER));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/class-role-endpoint");
        request.addHeader("Authorization", "Bearer token");

        assertThatThrownBy(() -> interceptor.preHandle(
                request,
                new MockHttpServletResponse(),
                handlerMethod(new AdminStubController(), "learnerOnly")
        ))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.FORBIDDEN.getMessage());
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME)).isNull();
    }

    @Test
    @DisplayName("클래스와 메서드 @RequireRole을 모두 만족하면 통과한다")
    void pre_handle_allows_when_method_role_satisfies_class_role() throws Exception {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(token -> new AuthenticatedMember(1L, MemberRole.ADMIN));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/class-role-endpoint");
        request.addHeader("Authorization", "Bearer token");

        boolean result = interceptor.preHandle(
                request,
                new MockHttpServletResponse(),
                handlerMethod(new AdminStubController(), "adminOnly")
        );

        assertThat(result).isTrue();
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME))
                .isEqualTo(new AuthenticatedMember(1L, MemberRole.ADMIN));
    }

    @Test
    @DisplayName("@RequireRole이 없고 보호 경로도 아니면 토큰 없이 통과한다")
    void pre_handle_skips_auth_for_public_endpoint() throws Exception {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/courses");

        boolean result = interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod("publicEndpoint"));

        assertThat(result).isTrue();
        assertThat(request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME)).isNull();
    }

    @Test
    @DisplayName("@RequireRole이 있는데 토큰이 없으면 TOKEN_REQUIRED 예외가 발생한다")
    void pre_handle_fails_when_require_role_annotation_present_but_no_token() throws Exception {
        JwtAuthenticationInterceptor interceptor = new JwtAuthenticationInterceptor(TokenValidatePortStub.INSTANCE);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/some-endpoint");

        assertThatThrownBy(() -> interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod("instructorOnly")))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_REQUIRED.getMessage());
    }

    // ─── 헬퍼 ─────────────────────────────────────────────────────────────

    private HandlerMethod handlerMethod(String methodName) throws NoSuchMethodException {
        return handlerMethod(new StubController(), methodName);
    }

    private HandlerMethod handlerMethod(Object controller, String methodName) throws NoSuchMethodException {
        Method method = controller.getClass().getDeclaredMethod(methodName);
        return new HandlerMethod(controller, method);
    }

    static class StubController {
        @RequireRole(MemberRole.INSTRUCTOR)
        void instructorOnly() {
        }

        void publicEndpoint() {
        }
    }

    @RequireRole(MemberRole.ADMIN)
    static class AdminStubController {
        @RequireRole(MemberRole.LEARNER)
        void learnerOnly() {
        }

        @RequireRole(MemberRole.ADMIN)
        void adminOnly() {
        }
    }

    private enum TokenValidatePortStub implements TokenValidatePort {
        INSTANCE;

        @Override
        public AuthenticatedMember validateAccessToken(String token) {
            return new AuthenticatedMember(1L, MemberRole.LEARNER);
        }
    }
}
