package com.ohgiraffers.lxp.course.presentation.web;

import com.ohgiraffers.lxp.course.application.port.in.DeleteCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.UpdateCourseUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseRequest;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseResponse;
import com.ohgiraffers.lxp.course.presentation.dto.UpdateCourseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final RegisterCourseUseCase registerCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;

    public CourseController(RegisterCourseUseCase registerCourseUseCase,
                            UpdateCourseUseCase updateCourseUseCase,
                            DeleteCourseUseCase deleteCourseUseCase) {
        this.registerCourseUseCase = registerCourseUseCase;
        this.updateCourseUseCase = updateCourseUseCase;
        this.deleteCourseUseCase = deleteCourseUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterCourseResponse> register(@RequestBody @Valid RegisterCourseRequest request) {
        Long courseId = registerCourseUseCase.register(request.toCommand());
        return ResponseEntity.status(201).body(new RegisterCourseResponse(courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateCourseRequest request) {
        updateCourseUseCase.update(request.toCommand(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCourseUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
