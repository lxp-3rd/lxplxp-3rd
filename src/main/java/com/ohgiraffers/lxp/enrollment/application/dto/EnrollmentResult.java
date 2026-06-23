package com.ohgiraffers.lxp.enrollment.application.dto;

import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import java.time.LocalDateTime;

public record EnrollmentResult(
        Long id,
        Long memberId,
        Long courseId,
        EnrollmentStatus status,
        LocalDateTime createdAt // 수강신청 일시
) {

}
