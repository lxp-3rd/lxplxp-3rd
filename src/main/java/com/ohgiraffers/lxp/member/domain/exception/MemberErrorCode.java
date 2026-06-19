package com.ohgiraffers.lxp.member.domain.exception;

import com.ohgiraffers.lxp.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER_409_001", "이미 사용 중인 이메일입니다."),
    PASSWORD_CONFIRM_NOT_MATCHED(HttpStatus.BAD_REQUEST, "MEMBER_400_001", "비밀번호 확인이 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
