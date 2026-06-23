package com.ohgiraffers.lxp.auth.presentation.support;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginMemberArgumentResolverTest {

    private final LoginMemberArgumentResolver resolver = new LoginMemberArgumentResolver();

    @Test
    void supports_parameter_with_login_member_annotation_and_authenticated_member_type() throws NoSuchMethodException {
        MethodParameter parameter = parameter("handler", AuthenticatedMember.class);

        assertThat(resolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    void resolve_argument_returns_authenticated_member_from_request_attribute() throws Exception {
        AuthenticatedMember authenticatedMember = new AuthenticatedMember(1L, MemberRole.LEARNER);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, authenticatedMember);

        Object result = resolver.resolveArgument(
                parameter("handler", AuthenticatedMember.class),
                null,
                new ServletWebRequest(request),
                null
        );

        assertThat(result).isEqualTo(authenticatedMember);
    }

    @Test
    void resolve_argument_fails_when_authenticated_member_attribute_is_missing() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThatThrownBy(() -> resolver.resolveArgument(
                parameter("handler", AuthenticatedMember.class),
                null,
                new ServletWebRequest(request),
                null
        ))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_REQUIRED);
    }

    private MethodParameter parameter(String methodName, Class<?> parameterType) throws NoSuchMethodException {
        Method method = getClass().getDeclaredMethod(methodName, parameterType);
        return new MethodParameter(method, 0);
    }

    @SuppressWarnings("unused")
    private void handler(@LoginMember AuthenticatedMember authenticatedMember) {
    }
}
