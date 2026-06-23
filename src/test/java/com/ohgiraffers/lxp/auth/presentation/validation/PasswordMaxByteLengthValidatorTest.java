package com.ohgiraffers.lxp.auth.presentation.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordMaxByteLengthValidatorTest {

    private final PasswordMaxByteLengthValidator validator = new PasswordMaxByteLengthValidator();

    @Test
    void valid_when_password_is_72_bytes() {
        validator.initialize(new AnnotationStub(72));

        assertThat(validator.isValid("a".repeat(72), null)).isTrue();
    }

    @Test
    void invalid_when_password_exceeds_72_bytes() {
        validator.initialize(new AnnotationStub(72));

        assertThat(validator.isValid("a".repeat(73), null)).isFalse();
    }

    @Test
    void invalid_when_utf8_password_exceeds_72_bytes() {
        validator.initialize(new AnnotationStub(72));

        assertThat(validator.isValid("\uAC00".repeat(25), null)).isFalse();
    }

    private record AnnotationStub(int max) implements PasswordMaxByteLength {

        @Override
        public String message() {
            return "";
        }

        @Override
        public Class<?>[] groups() {
            return new Class<?>[0];
        }

        @Override
        @SuppressWarnings("unchecked")
        public Class<? extends jakarta.validation.Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<PasswordMaxByteLength> annotationType() {
            return PasswordMaxByteLength.class;
        }
    }
}
