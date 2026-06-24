package com.ohgiraffers.lxp.instructor.presentation.web;

import com.ohgiraffers.lxp.auth.presentation.support.RequireRole;
import com.ohgiraffers.lxp.instructor.application.port.in.GetInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.UpdateInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.presentation.dto.InstructorProfileResponse;
import com.ohgiraffers.lxp.instructor.presentation.dto.RegisterInstructorProfileRequest;
import com.ohgiraffers.lxp.instructor.presentation.dto.UpdateInstructorProfileRequest;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorProfileController {

    private final RegisterInstructorProfileUseCase registerInstructorProfileUseCase;
    private final UpdateInstructorProfileUseCase updateInstructorProfileUseCase;
    private final GetInstructorProfileUseCase getInstructorProfileUseCase;

    public InstructorProfileController(
            RegisterInstructorProfileUseCase registerInstructorProfileUseCase,
            UpdateInstructorProfileUseCase updateInstructorProfileUseCase,
            GetInstructorProfileUseCase getInstructorProfileUseCase
    ) {
        this.registerInstructorProfileUseCase = registerInstructorProfileUseCase;
        this.updateInstructorProfileUseCase = updateInstructorProfileUseCase;
        this.getInstructorProfileUseCase = getInstructorProfileUseCase;
    }

    @RequireRole(MemberRole.INSTRUCTOR)
    @PostMapping("/{id}/profile")
    public ResponseEntity<Void> register(
            @PathVariable Long id,
            @RequestBody @Valid RegisterInstructorProfileRequest request
    ) {
        registerInstructorProfileUseCase.register(request.toCommand(id));
        return ResponseEntity.status(201).build();
    }

    @RequireRole(MemberRole.INSTRUCTOR)
    @PutMapping("/{id}/profile")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateInstructorProfileRequest request
    ) {
        updateInstructorProfileUseCase.update(request.toCommand(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<InstructorProfileResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(InstructorProfileResponse.from(getInstructorProfileUseCase.get(id)));
    }
}
