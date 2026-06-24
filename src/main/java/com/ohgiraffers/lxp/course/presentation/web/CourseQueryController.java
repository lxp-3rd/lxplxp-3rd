package com.ohgiraffers.lxp.course.presentation.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseDetailUseCase;
import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.CourseDetailResponse;
import com.ohgiraffers.lxp.course.presentation.dto.CourseSummaryResponse;
import com.ohgiraffers.lxp.course.presentation.support.OptionalMemberResolver;

/**
 * 강좌 조회(공개, 비로그인 접근 가능) 읽기 전용 엔드포인트.
 */
@RestController
@RequestMapping("/api/courses")
public class CourseQueryController {

    private final GetCourseListUseCase getCourseListUseCase;
    private final GetCourseDetailUseCase getCourseDetailUseCase;
    private final OptionalMemberResolver optionalMemberResolver;

    public CourseQueryController(GetCourseListUseCase getCourseListUseCase,
                                 GetCourseDetailUseCase getCourseDetailUseCase,
                                 OptionalMemberResolver optionalMemberResolver) {
        this.getCourseListUseCase = getCourseListUseCase;
        this.getCourseDetailUseCase = getCourseDetailUseCase;
        this.optionalMemberResolver = optionalMemberResolver;
    }

    @GetMapping
    public ResponseEntity<List<CourseSummaryResponse>> getCourseList() {
        List<CourseSummaryResponse> responses = getCourseListUseCase.getCourseList().stream()
                .map(CourseSummaryResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailResponse> getCourseDetail(
            @PathVariable Long courseId,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long memberId = optionalMemberResolver.resolveMemberId(authorization);
        CourseDetailResponse response =
                CourseDetailResponse.from(getCourseDetailUseCase.getCourseDetail(courseId, memberId));
        return ResponseEntity.ok(response);
    }
}
