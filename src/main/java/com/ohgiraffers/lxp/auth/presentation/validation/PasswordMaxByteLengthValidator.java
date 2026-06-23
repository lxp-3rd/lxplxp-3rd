package com.ohgiraffers.lxp.auth.presentation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.charset.StandardCharsets;

public class PasswordMaxByteLengthValidator implements ConstraintValidator<PasswordMaxByteLength, String> {

    private int max;

    @Override
    public void initialize(PasswordMaxByteLength constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.getBytes(StandardCharsets.UTF_8).length <= max;
    }
}
