package com.ohgiraffers.lxp.course.presentation.web;

import com.ohgiraffers.lxp.course.application.port.in.LikeCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.UnlikeCourseUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.LikeCourseRequest;
import com.ohgiraffers.lxp.course.presentation.dto.UnlikeCourseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseLikeController {

    private final LikeCourseUseCase likeCourseUseCase;
    private final UnlikeCourseUseCase unlikeCourseUseCase;

    public CourseLikeController(LikeCourseUseCase likeCourseUseCase,
                                UnlikeCourseUseCase unlikeCourseUseCase) {
        this.likeCourseUseCase = likeCourseUseCase;
        this.unlikeCourseUseCase = unlikeCourseUseCase;
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<Void> like(@PathVariable Long id,
                                     @RequestBody @Valid LikeCourseRequest request) {
        likeCourseUseCase.like(request.toCommand(id));
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<Void> unlike(@PathVariable Long id,
                                       @RequestBody @Valid UnlikeCourseRequest request) {
        unlikeCourseUseCase.unlike(request.toCommand(id));
        return ResponseEntity.noContent().build();
    }
}
