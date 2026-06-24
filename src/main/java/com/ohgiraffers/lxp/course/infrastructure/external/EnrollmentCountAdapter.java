package com.ohgiraffers.lxp.course.infrastructure.external;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentCountPort;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository.CourseEnrollmentCount;

/**
 * enrollment 컨텍스트의 수강 데이터를 batch로 집계해 Course 측 out-port를 구현한다.
 * application 레이어는 enrollment에 직접 의존하지 않고 이 포트 인터페이스만 본다(DIP).
 */
@Component
public class EnrollmentCountAdapter implements LoadEnrollmentCountPort {

    private final EnrollmentJpaRepository enrollmentJpaRepository;

    public EnrollmentCountAdapter(EnrollmentJpaRepository enrollmentJpaRepository) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
    }

    @Override
    public Map<Long, Long> countByCourseIds(List<Long> courseIds) {
        if (courseIds.isEmpty()) {
            return Map.of();
        }
        return enrollmentJpaRepository.countActiveByCourseIds(courseIds).stream()
                .collect(Collectors.toMap(
                        CourseEnrollmentCount::getCourseId,
                        CourseEnrollmentCount::getCount
                ));
    }
}
