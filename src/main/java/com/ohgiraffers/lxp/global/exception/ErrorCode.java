package com.ohgiraffers.lxp.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // instructor
    INSTRUCTOR_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "강사 신청 내역을 찾을 수 없습니다."),
    INSTRUCTOR_APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 신청한 내역이 있습니다."),
    INSTRUCTOR_NOT_FOUND(HttpStatus.NOT_FOUND, "강사 정보를 찾을 수 없습니다."),
    INSTRUCTOR_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "강사 프로필을 찾을 수 없습니다."),
    INSTRUCTOR_PROFILE_ALREADY_EXISTS(HttpStatus.CONFLICT, "강사 프로필이 이미 존재합니다."),

    // course
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "강좌를 찾을 수 없습니다."),
    COURSE_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 강좌입니다."),
    COURSE_STATUS_CHANGE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "강좌 상태를 변경할 수 없습니다."),
    COURSE_LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 좋아요를 눌렀습니다."),
    COURSE_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 내역을 찾을 수 없습니다."),

    // content
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘텐츠를 찾을 수 없습니다."),
    CONTENT_LEARNING_NOT_FOUND(HttpStatus.NOT_FOUND, "학습 이력을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
