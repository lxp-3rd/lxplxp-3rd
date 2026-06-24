package com.ohgiraffers.lxp.course.presentation.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.CourseSummaryResponse;

/**
 * 강좌 전체 조회(공개, 비로그인 접근 가능) 읽기 전용 엔드포인트.
 */
@RestController
@RequestMapping("/api/courses")
public class CourseQueryController {

    private final GetCourseListUseCase getCourseListUseCase;

    public CourseQueryController(GetCourseListUseCase getCourseListUseCase) {
        this.getCourseListUseCase = getCourseListUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CourseSummaryResponse>> getCourseList() {
        List<CourseSummaryResponse> responses = getCourseListUseCase.getCourseList().stream()
                .map(CourseSummaryResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
