package com.ohgiraffers.lxp.enrollment.presentation.web;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.in.EnrollUseCase;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentRequest;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollUseCase enrollUseCase;

    public EnrollmentController(EnrollUseCase enrollUseCase) {
        this.enrollUseCase = enrollUseCase;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(
            @Valid @RequestBody EnrollmentRequest request) {
        EnrollmentResult result = enrollUseCase.enroll(request.toCommand());
        return ResponseEntity
                .created(URI.create("/enrollments/" + result.id()))
                .body(EnrollmentResponse.from(result));
    }

}
