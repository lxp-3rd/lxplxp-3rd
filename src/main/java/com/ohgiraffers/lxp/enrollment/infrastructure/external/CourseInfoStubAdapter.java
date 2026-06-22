package com.ohgiraffers.lxp.enrollment.infrastructure.external;

import com.ohgiraffers.lxp.enrollment.application.dto.CourseInfo;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadCourseInfoPort;
import org.springframework.stereotype.Component;

/**
 * LoadCourseInfoPort 스텁. course 도메인 미구현 상태에서 항상 공개된 강좌를 응답한다.
 * TODO: course 도메인 구현 후 실제 조회 어댑터로 교체
 */
@Component
public class CourseInfoStubAdapter implements LoadCourseInfoPort {

    @Override
    public CourseInfo load(Long courseId) {
        return new CourseInfo(courseId, true);
    }
    
}
