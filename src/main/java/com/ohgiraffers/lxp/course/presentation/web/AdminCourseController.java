package com.ohgiraffers.lxp.course.presentation.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohgiraffers.lxp.course.application.port.in.GetAdminCourseListUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.AdminCourseResponse;

/**
 * 관리자 강좌 관리 엔드포인트. /api/admin/** 는 인터셉터에서 ADMIN 역할로 보호된다.
 * 공개/비공개 전환과 삭제는 기존 /api/courses/{id}/status, /api/courses/{id} 를 재사용한다.
 */
@RestController
@RequestMapping("/api/admin/courses")
public class AdminCourseController {

    private final GetAdminCourseListUseCase getAdminCourseListUseCase;

    public AdminCourseController(GetAdminCourseListUseCase getAdminCourseListUseCase) {
        this.getAdminCourseListUseCase = getAdminCourseListUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AdminCourseResponse>> getCourses() {
        List<AdminCourseResponse> response = getAdminCourseListUseCase.getAdminCourseList().stream()
                .map(AdminCourseResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}