package com.ohgiraffers.lxp.course.presentation.web;

import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseRequest;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final RegisterCourseUseCase registerCourseUseCase;

    public CourseController(RegisterCourseUseCase registerCourseUseCase) {
        this.registerCourseUseCase = registerCourseUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterCourseResponse> register(@RequestBody @Valid RegisterCourseRequest request) {
        Long courseId = registerCourseUseCase.register(request.toCommand());
        return ResponseEntity.status(201).body(new RegisterCourseResponse(courseId));
    }
}
