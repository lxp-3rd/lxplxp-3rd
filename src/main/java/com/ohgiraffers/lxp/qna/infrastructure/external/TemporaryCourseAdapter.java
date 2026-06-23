package com.ohgiraffers.lxp.qna.infrastructure.external;

import com.ohgiraffers.lxp.qna.application.port.out.CourseQueryPort;
import com.ohgiraffers.lxp.qna.application.port.out.EnrollmentQueryPort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TemporaryCourseAdapter implements CourseQueryPort, EnrollmentQueryPort {

    private static final Set<Long> COURSE_IDS = Set.of(1L, 2L, 3L);

    private static final Set<TemporaryEnrollment> ENROLLMENTS = Set.of(
            new TemporaryEnrollment(1L, 1L),
            new TemporaryEnrollment(1L, 2L),
            new TemporaryEnrollment(2L, 2L),
            new TemporaryEnrollment(2L, 3L),
            new TemporaryEnrollment(3L, 1L)
    );

    @Override
    public boolean existsCourse(Long courseId) {
        return COURSE_IDS.contains(courseId);
    }

    @Override
    public boolean isEnrolled(Long courseId, Long memberId) {
        return ENROLLMENTS.contains(new TemporaryEnrollment(courseId, memberId));
    }

    private record TemporaryEnrollment(Long courseId, Long memberId) {
    }
}
