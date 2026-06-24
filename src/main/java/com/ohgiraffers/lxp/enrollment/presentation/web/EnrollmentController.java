package com.ohgiraffers.lxp.enrollment.presentation.web;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.command.CancelEnrollmentCommand;
import com.ohgiraffers.lxp.enrollment.application.port.command.CompleteEnrollmentCommand;
import com.ohgiraffers.lxp.enrollment.application.port.in.CancelEnrollmentUseCase;
import com.ohgiraffers.lxp.enrollment.application.port.in.CompleteEnrollmentUseCase;
import com.ohgiraffers.lxp.enrollment.application.port.in.EnrollUseCase;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentRequest;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentResponse;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/enrollments", "/api/enrollments"})
public class EnrollmentController {

    private final EnrollUseCase enrollUseCase;
    private final CancelEnrollmentUseCase cancelEnrollmentUseCase;
    private final CompleteEnrollmentUseCase completeEnrollmentUseCase;
    private final EnrollmentJpaRepository enrollmentRepository;

    public EnrollmentController(
            EnrollUseCase enrollUseCase,
            CancelEnrollmentUseCase cancelEnrollmentUseCase,
            CompleteEnrollmentUseCase completeEnrollmentUseCase,
            EnrollmentJpaRepository enrollmentRepository) {
        this.enrollUseCase = enrollUseCase;
        this.cancelEnrollmentUseCase = cancelEnrollmentUseCase;
        this.completeEnrollmentUseCase = completeEnrollmentUseCase;
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getEnrollments(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long courseId
    ) {
        List<EnrollmentJpaEntity> enrollments;
        if (memberId != null) {
            enrollments = enrollmentRepository.findAllByMemberIdAndDeletedAtIsNullOrderByCreatedAtDesc(memberId);
        } else if (courseId != null) {
            enrollments = enrollmentRepository.findAllByCourseIdAndDeletedAtIsNullOrderByCreatedAtDesc(courseId);
        } else {
            enrollments = enrollmentRepository.findAll().stream()
                    .filter(enrollment -> enrollment.getDeletedAt() == null)
                    .toList();
        }
        return ResponseEntity.ok(enrollments.stream().map(EnrollmentResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollment(@PathVariable Long id) {
        EnrollmentJpaEntity enrollment = enrollmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND));
        return ResponseEntity.ok(EnrollmentResponse.from(enrollment));
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(@Valid @RequestBody EnrollmentRequest request) {
        EnrollmentResult result = enrollUseCase.enroll(request.toCommand());
        return ResponseEntity
                .created(URI.create("/enrollments/" + result.id()))
                .body(EnrollmentResponse.from(result));
    }

    @PostMapping("/{id}/progress")
    public ResponseEntity<EnrollmentResponse> complete(@PathVariable Long id) {
        EnrollmentResult result = completeEnrollmentUseCase.complete(new CompleteEnrollmentCommand(id));
        return ResponseEntity.ok(EnrollmentResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> cancel(@PathVariable Long id) {
        EnrollmentResult result = cancelEnrollmentUseCase.cancel(new CancelEnrollmentCommand(id));
        return ResponseEntity.ok(EnrollmentResponse.from(result));
    }

}
