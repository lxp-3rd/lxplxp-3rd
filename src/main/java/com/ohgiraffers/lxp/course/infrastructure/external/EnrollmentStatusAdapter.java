package com.ohgiraffers.lxp.course.infrastructure.external;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ohgiraffers.lxp.course.application.port.out.EnrollmentStatusView;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentStatusPort;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository.CourseEnrollmentCount;

/**
 * enrollment 컨텍스트에서 강좌 수강 현황(현재 수강 중 인원 수 + 수강 여부)을 조회해 Course 측 out-port를 구현한다.
 */
@Component
public class EnrollmentStatusAdapter implements LoadEnrollmentStatusPort {

    private final EnrollmentJpaRepository enrollmentJpaRepository;

    public EnrollmentStatusAdapter(EnrollmentJpaRepository enrollmentJpaRepository) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
    }

    @Override
    public EnrollmentStatusView load(Long courseId, Long memberId) {
        long enrollmentCount = enrollmentJpaRepository.countActiveByCourseIds(List.of(courseId)).stream()
                .findFirst()
                .map(CourseEnrollmentCount::getCount)
                .orElse(0L);

        boolean enrolled = memberId != null
                && enrollmentJpaRepository.existsByMemberIdAndCourseIdAndStatus(
                        memberId, courseId, EnrollmentStatus.ACTIVE);

        return new EnrollmentStatusView(enrollmentCount, enrolled);
    }
}
