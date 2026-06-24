package com.ohgiraffers.lxp.auth.presentation.support;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    MemberRole[] value();
}
