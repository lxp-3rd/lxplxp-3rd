package com.ohgiraffers.lxp.course.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseListView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseLikeCountPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseListPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentCountPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;
import com.ohgiraffers.lxp.course.domain.model.read.CourseSummary;

/**
 * 강좌 전체 조회(읽기 전용). PUBLIC 강좌를 created_at DESC로 조회한 뒤,
 * 수강생 수(enrollment)·강사명(member)·좋아요 수(course_like)를 각각 batch 1회로 조합한다.
 * 총 쿼리: 강좌 조회 1 + 수강수 1 + 강사명 1 + 좋아요 1 (강좌 수와 무관).
 */
@Service
@Transactional(readOnly = true)
public class GetCourseListService implements GetCourseListUseCase {

    private final LoadCourseListPort loadCourseListPort;
    private final LoadEnrollmentCountPort loadEnrollmentCountPort;
    private final LoadInstructorNamePort loadInstructorNamePort;
    private final LoadCourseLikeCountPort loadCourseLikeCountPort;

    public GetCourseListService(LoadCourseListPort loadCourseListPort,
                                LoadEnrollmentCountPort loadEnrollmentCountPort,
                                LoadInstructorNamePort loadInstructorNamePort,
                                LoadCourseLikeCountPort loadCourseLikeCountPort) {
        this.loadCourseListPort = loadCourseListPort;
        this.loadEnrollmentCountPort = loadEnrollmentCountPort;
        this.loadInstructorNamePort = loadInstructorNamePort;
        this.loadCourseLikeCountPort = loadCourseLikeCountPort;
    }

    @Override
    public List<CourseSummary> getCourseList() {
        List<CourseListView> courses = loadCourseListPort.loadPublicCourses();

        List<Long> courseIds = courses.stream().map(CourseListView::id).toList();
        List<Long> instructorIds = courses.stream().map(CourseListView::instructorId).distinct().toList();

        Map<Long, Long> enrollmentCounts = loadEnrollmentCountPort.countByCourseIds(courseIds);
        Map<Long, String> instructorNames = loadInstructorNamePort.findNamesByInstructorIds(instructorIds);
        Map<Long, Long> likeCounts = loadCourseLikeCountPort.countByCourseIds(courseIds);

        return courses.stream()
                .map(course -> new CourseSummary(
                        course.id(),
                        course.title(),
                        course.thumbnailUrl(),
                        instructorNames.get(course.instructorId()),
                        enrollmentCounts.getOrDefault(course.id(), 0L),
                        likeCounts.getOrDefault(course.id(), 0L)
                ))
                .toList();
    }
}
