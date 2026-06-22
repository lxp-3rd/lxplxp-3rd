package com.ohgiraffers.lxp.enrollment.application.port.out;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;

/**
 * 수강 저장 아웃바운드 포트. 저장 후 영속성 계층이 채운 식별자·신청일시(createdAt)를 포함한 표현을 반환한다.
 */
public interface SaveEnrollmentPort {

    EnrollmentResult save(Enrollment enrollment);
}
