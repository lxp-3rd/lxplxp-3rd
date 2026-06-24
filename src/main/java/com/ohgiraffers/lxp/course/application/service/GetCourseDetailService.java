package com.ohgiraffers.lxp.course.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseDetailUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseDetailView;
import com.ohgiraffers.lxp.course.application.port.out.EnrollmentStatusView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseDetailPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentStatusPort;
import com.ohgiraffers.lxp.course.domain.model.read.CourseDetail;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

/**
 * 강좌 상세 조회(읽기 전용). PUBLIC 강좌 상세 + 커리큘럼을 조회한 뒤
 * 수강 인원 수·수강 여부(enrollment)를 조합한다.
 */
@Service
@Transactional(readOnly = true)
public class GetCourseDetailService implements GetCourseDetailUseCase {

    private final LoadCourseDetailPort loadCourseDetailPort;
    private final LoadEnrollmentStatusPort loadEnrollmentStatusPort;

    public GetCourseDetailService(LoadCourseDetailPort loadCourseDetailPort,
                                  LoadEnrollmentStatusPort loadEnrollmentStatusPort) {
        this.loadCourseDetailPort = loadCourseDetailPort;
        this.loadEnrollmentStatusPort = loadEnrollmentStatusPort;
    }

    @Override
    public CourseDetail getCourseDetail(Long courseId, Long memberId) {
        CourseDetailView view = loadCourseDetailPort.loadPublicCourseDetail(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));

        EnrollmentStatusView status = loadEnrollmentStatusPort.load(courseId, memberId);

        return new CourseDetail(
                view.id(),
                view.title(),
                view.description(),
                view.thumbnailUrl(),
                status.enrollmentCount(),
                status.enrolled(),
                view.curriculum()
        );
    }
}
