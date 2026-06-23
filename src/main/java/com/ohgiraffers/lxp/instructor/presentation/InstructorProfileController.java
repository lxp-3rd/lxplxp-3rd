package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.UpdateInstructorProfileUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorProfileController {

    private final RegisterInstructorProfileUseCase registerInstructorProfileUseCase;
    private final UpdateInstructorProfileUseCase updateInstructorProfileUseCase;

    public InstructorProfileController(
            RegisterInstructorProfileUseCase registerInstructorProfileUseCase,
            UpdateInstructorProfileUseCase updateInstructorProfileUseCase
    ) {
        this.registerInstructorProfileUseCase = registerInstructorProfileUseCase;
        this.updateInstructorProfileUseCase = updateInstructorProfileUseCase;
    }

    @PostMapping("/{id}/profile")
    public ResponseEntity<Void> register(
            @PathVariable Long id,
            @RequestBody @Valid RegisterInstructorProfileRequest request
    ) {
        registerInstructorProfileUseCase.register(request.toCommand(id));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateInstructorProfileRequest request
    ) {
        updateInstructorProfileUseCase.update(request.toCommand(id));
        return ResponseEntity.ok().build();
    }
}
