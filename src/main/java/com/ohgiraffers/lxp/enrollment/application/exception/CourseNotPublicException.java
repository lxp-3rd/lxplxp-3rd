package com.ohgiraffers.lxp.enrollment.application.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

/**
 * 비공개 강좌에 수강신청할 때 발생.
 * ErrorCode 매핑: COURSE_NOT_PUBLIC (403 FORBIDDEN)
 */
public class CourseNotPublicException extends BusinessException {

    public CourseNotPublicException() {
        super(ErrorCode.COURSE_NOT_PUBLIC);
    }
}
