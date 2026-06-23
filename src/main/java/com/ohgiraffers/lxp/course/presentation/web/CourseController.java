package com.ohgiraffers.lxp.course.presentation.web;

import com.ohgiraffers.lxp.course.application.port.in.ChangeCourseStatusUseCase;
import com.ohgiraffers.lxp.course.application.port.in.DeleteCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.application.port.in.GetCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.UpdateCourseUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.ChangeCourseStatusRequest;
import com.ohgiraffers.lxp.course.presentation.dto.CourseResponse;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseRequest;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseResponse;
import com.ohgiraffers.lxp.course.presentation.dto.UpdateCourseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final RegisterCourseUseCase registerCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final ChangeCourseStatusUseCase changeCourseStatusUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final GetCourseListUseCase getCourseListUseCase;

    public CourseController(RegisterCourseUseCase registerCourseUseCase,
                            UpdateCourseUseCase updateCourseUseCase,
                            DeleteCourseUseCase deleteCourseUseCase,
                            ChangeCourseStatusUseCase changeCourseStatusUseCase,
                            GetCourseUseCase getCourseUseCase,
                            GetCourseListUseCase getCourseListUseCase) {
        this.registerCourseUseCase = registerCourseUseCase;
        this.updateCourseUseCase = updateCourseUseCase;
        this.deleteCourseUseCase = deleteCourseUseCase;
        this.changeCourseStatusUseCase = changeCourseStatusUseCase;
        this.getCourseUseCase = getCourseUseCase;
        this.getCourseListUseCase = getCourseListUseCase;
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id,
                                             @RequestBody @Valid ChangeCourseStatusRequest request) {
        changeCourseStatusUseCase.changeStatus(request.toCommand(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(CourseResponse.from(getCourseUseCase.getCourse(id)));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getCourseList() {
        List<CourseResponse> responses = getCourseListUseCase.getCourseList().stream()
                .map(CourseResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
