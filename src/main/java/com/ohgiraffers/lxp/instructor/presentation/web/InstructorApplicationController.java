package com.ohgiraffers.lxp.instructor.presentation.web;

import com.ohgiraffers.lxp.auth.presentation.support.RequireRole;
import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.GetInstructorApplicationListUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase;
import com.ohgiraffers.lxp.instructor.presentation.dto.ApplyInstructorRequest;
import com.ohgiraffers.lxp.instructor.presentation.dto.InstructorApplicationResponse;
import com.ohgiraffers.lxp.instructor.presentation.dto.ReviewInstructorApplicationRequest;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorApplicationController {

    private final ApplyInstructorUseCase applyInstructorUseCase;
    private final ReviewInstructorApplicationUseCase reviewInstructorApplicationUseCase;
    private final GetInstructorApplicationListUseCase getInstructorApplicationListUseCase;

    public InstructorApplicationController(
            ApplyInstructorUseCase applyInstructorUseCase,
            ReviewInstructorApplicationUseCase reviewInstructorApplicationUseCase,
            GetInstructorApplicationListUseCase getInstructorApplicationListUseCase
    ) {
        this.applyInstructorUseCase = applyInstructorUseCase;
        this.reviewInstructorApplicationUseCase = reviewInstructorApplicationUseCase;
        this.getInstructorApplicationListUseCase = getInstructorApplicationListUseCase;
    }

    @RequireRole(MemberRole.ADMIN)
    @GetMapping("/applications")
    public ResponseEntity<List<InstructorApplicationResponse>> getApplications() {
        return ResponseEntity.ok(
                getInstructorApplicationListUseCase.getApplications().stream()
                        .map(InstructorApplicationResponse::from)
                        .toList()
        );
    }

    @RequireRole(MemberRole.LEARNER)
    @PostMapping("/applications")
    public ResponseEntity<Void> apply(@RequestBody @Valid ApplyInstructorRequest request) {
        applyInstructorUseCase.apply(request.toCommand());
        return ResponseEntity.status(201).build();
    }

    @RequireRole(MemberRole.ADMIN)
    @PatchMapping("/applications/{id}")
    public ResponseEntity<Void> review(
            @PathVariable Long id,
            @RequestBody @Valid ReviewInstructorApplicationRequest request
    ) {
        reviewInstructorApplicationUseCase.review(request.toCommand(id));
        return ResponseEntity.ok().build();
    }
}
