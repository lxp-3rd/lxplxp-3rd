package com.ohgiraffers.lxp.enrollment.application.port.out;

import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import java.util.Optional;

public interface LoadEnrollmentPort {

    boolean existsActiveEnrollment(Long memberId, Long courseId);

    Optional<Enrollment> findById(Long id);

}
