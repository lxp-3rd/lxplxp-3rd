package com.ohgiraffers.lxp.course.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohgiraffers.lxp.course.application.dto.AdminCourseResult;
import com.ohgiraffers.lxp.course.application.port.in.GetAdminCourseListUseCase;
import com.ohgiraffers.lxp.course.application.port.out.AdminCourseListView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseListPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentCountPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;

/**
 * 관리자 강좌 관리 목록 조회(읽기 전용). 상태 무관 전체 강좌를 created_at DESC로 조회한 뒤,
 * 강사명(member)·수강생 수(enrollment)를 각각 batch 1회로 조합한다.
 * 총 쿼리: 강좌 조회 1 + 강사명 1 + 수강수 1 (강좌 수와 무관).
 */
@Service
@Transactional(readOnly = true)
public class GetAdminCourseListService implements GetAdminCourseListUseCase {

    private final LoadCourseListPort loadCourseListPort;
    private final LoadEnrollmentCountPort loadEnrollmentCountPort;
    private final LoadInstructorNamePort loadInstructorNamePort;

    public GetAdminCourseListService(LoadCourseListPort loadCourseListPort,
                                     LoadEnrollmentCountPort loadEnrollmentCountPort,
                                     LoadInstructorNamePort loadInstructorNamePort) {
        this.loadCourseListPort = loadCourseListPort;
        this.loadEnrollmentCountPort = loadEnrollmentCountPort;
        this.loadInstructorNamePort = loadInstructorNamePort;
    }

    @Override
    public List<AdminCourseResult> getAdminCourseList() {
        List<AdminCourseListView> courses = loadCourseListPort.loadAllCourses();

        List<Long> courseIds = courses.stream().map(AdminCourseListView::id).toList();
        List<Long> instructorIds = courses.stream().map(AdminCourseListView::instructorId).distinct().toList();

        Map<Long, Long> enrollmentCounts = loadEnrollmentCountPort.countByCourseIds(courseIds);
        Map<Long, String> instructorNames = loadInstructorNamePort.findNamesByInstructorIds(instructorIds);

        return courses.stream()
                .map(course -> new AdminCourseResult(
                        course.id(),
                        course.title(),
                        course.thumbnailUrl(),
                        instructorNames.get(course.instructorId()),
                        enrollmentCounts.getOrDefault(course.id(), 0L),
                        course.status(),
                        course.hiddenBy()
                ))
                .toList();
    }
}