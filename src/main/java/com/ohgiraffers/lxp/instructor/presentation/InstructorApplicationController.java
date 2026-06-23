package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructors")
public class InstructorApplicationController {

    private final ApplyInstructorUseCase applyInstructorUseCase;

    public InstructorApplicationController(ApplyInstructorUseCase applyInstructorUseCase) {
        this.applyInstructorUseCase = applyInstructorUseCase;
    }

    @PostMapping("/applications")
    public ResponseEntity<Void> apply(@RequestBody @Valid ApplyInstructorRequest request) {
        applyInstructorUseCase.apply(request.toCommand());
        return ResponseEntity.status(201).build();
    }
}
