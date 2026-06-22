package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorApplicationController {

    private final ApplyInstructorUseCase applyInstructorUseCase;
    private final ReviewInstructorApplicationUseCase reviewInstructorApplicationUseCase;

    public InstructorApplicationController(
            ApplyInstructorUseCase applyInstructorUseCase,
            ReviewInstructorApplicationUseCase reviewInstructorApplicationUseCase
    ) {
        this.applyInstructorUseCase = applyInstructorUseCase;
        this.reviewInstructorApplicationUseCase = reviewInstructorApplicationUseCase;
    }

    @PostMapping("/applications")
    public ResponseEntity<Void> apply(@RequestBody @Valid ApplyInstructorRequest request) {
        applyInstructorUseCase.apply(request.toCommand());
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/applications/{id}")
    public ResponseEntity<Void> review(
            @PathVariable Long id,
            @RequestBody @Valid ReviewInstructorApplicationRequest request
    ) {
        reviewInstructorApplicationUseCase.review(request.toCommand(id));
        return ResponseEntity.ok().build();
    }
}
