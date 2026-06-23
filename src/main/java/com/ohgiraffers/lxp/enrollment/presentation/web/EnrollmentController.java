package com.ohgiraffers.lxp.enrollment.presentation.web;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.command.CancelEnrollmentCommand;
import com.ohgiraffers.lxp.enrollment.application.port.in.CancelEnrollmentUseCase;
import com.ohgiraffers.lxp.enrollment.application.port.in.EnrollUseCase;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentRequest;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollUseCase enrollUseCase;
    private final CancelEnrollmentUseCase cancelEnrollmentUseCase;

    public EnrollmentController(
            EnrollUseCase enrollUseCase,
            CancelEnrollmentUseCase cancelEnrollmentUseCase) {
        this.enrollUseCase = enrollUseCase;
        this.cancelEnrollmentUseCase = cancelEnrollmentUseCase;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(
            @Valid @RequestBody EnrollmentRequest request) {
        EnrollmentResult result = enrollUseCase.enroll(request.toCommand());
        return ResponseEntity
                .created(URI.create("/enrollments/" + result.id()))
                .body(EnrollmentResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> cancel(@PathVariable Long id) {
        EnrollmentResult result = cancelEnrollmentUseCase.cancel(new CancelEnrollmentCommand(id));
        return ResponseEntity.ok(EnrollmentResponse.from(result));
    }

}
