package com.ohgiraffers.lxp.enrollment.infrastructure.external;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaRepository;
import com.ohgiraffers.lxp.enrollment.application.dto.CourseInfo;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadCourseInfoPort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class CourseInfoAdapter implements LoadCourseInfoPort {

    private final CourseJpaRepository courseJpaRepository;

    public CourseInfoAdapter(CourseJpaRepository courseJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
    }

    @Override
    public CourseInfo load(Long courseId) {
        var entity = courseJpaRepository.findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
        return new CourseInfo(courseId, entity.getStatus() == CourseStatus.PUBLIC);
    }
}
