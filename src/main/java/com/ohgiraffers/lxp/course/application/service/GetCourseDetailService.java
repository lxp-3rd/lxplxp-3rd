package com.ohgiraffers.lxp.course.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseDetailUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseDetailView;
import com.ohgiraffers.lxp.course.application.port.out.EnrollmentStatusView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseDetailPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentStatusPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;
import com.ohgiraffers.lxp.course.domain.model.read.CourseDetail;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
public class GetCourseDetailService implements GetCourseDetailUseCase {

    private final LoadCourseDetailPort loadCourseDetailPort;
    private final LoadEnrollmentStatusPort loadEnrollmentStatusPort;
    private final LoadInstructorNamePort loadInstructorNamePort;

    public GetCourseDetailService(LoadCourseDetailPort loadCourseDetailPort,
                                  LoadEnrollmentStatusPort loadEnrollmentStatusPort,
                                  LoadInstructorNamePort loadInstructorNamePort) {
        this.loadCourseDetailPort = loadCourseDetailPort;
        this.loadEnrollmentStatusPort = loadEnrollmentStatusPort;
        this.loadInstructorNamePort = loadInstructorNamePort;
    }

    @Override
    public CourseDetail getCourseDetail(Long courseId, Long memberId) {
        CourseDetailView view = loadCourseDetailPort.loadPublicCourseDetail(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));

        EnrollmentStatusView status = loadEnrollmentStatusPort.load(courseId, memberId);

        Map<Long, String> instructorNames = loadInstructorNamePort
                .findNamesByInstructorIds(List.of(view.instructorId()));
        String instructorName = instructorNames.getOrDefault(view.instructorId(), "");

        return new CourseDetail(
                view.id(),
                view.instructorId(),
                instructorName,
                view.title(),
                view.summary(),
                view.description(),
                view.thumbnailUrl(),
                status.enrollmentCount(),
                status.enrolled(),
                view.curriculum()
        );
    }
}
