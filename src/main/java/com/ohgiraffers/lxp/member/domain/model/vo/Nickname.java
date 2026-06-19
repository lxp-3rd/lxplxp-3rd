package com.ohgiraffers.lxp.member.domain.model.vo;

import java.util.Objects;

public record Nickname(String value) {

    private static final int MAX_LENGTH = 30;

    public Nickname {
        Objects.requireNonNull(value, "닉네임은 필수입니다.");
        if (value.isBlank()) {
            throw new IllegalArgumentException("닉네임은 공백일 수 없습니다.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임은 30자 이하여야 합니다.");
        }
    }
}
