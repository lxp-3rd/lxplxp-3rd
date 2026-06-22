package com.ohgiraffers.lxp.member.domain.model.vo;

import java.util.Objects;

public record EncodedPassword(String value) {

    public EncodedPassword {
        Objects.requireNonNull(value, "비밀번호는 필수입니다.");
        if (value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 공백일 수 없습니다.");
        }
    }
}
