package com.ohgiraffers.lxp.enrollment.domain.model.entity;

import com.ohgiraffers.lxp.enrollment.domain.exception.EnrollmentAlreadyCanceledException;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;

public class Enrollment {

    private final Long id;
    private final Long memberId;
    private final Long courseId;
    private EnrollmentStatus status;

    private Enrollment(Long id, Long memberId, Long courseId, EnrollmentStatus status) {
        validate(memberId, courseId, status);
        this.id = id;
        this.memberId = memberId;
        this.courseId = courseId;
        this.status = status;
    }

    private void validate(Long memberId, Long courseId, EnrollmentStatus status) {
        requireNonNull(memberId, "memberId");
        requireNonNull(courseId, "courseId");
        requireNonNull(status, "status");
    }

    private void requireNonNull(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " must not be null");
        }
    }

    public static Enrollment enroll(Long memberId, Long courseId) {
        return new Enrollment(null, memberId, courseId, EnrollmentStatus.ACTIVE);
    }

    public static Enrollment restore(Long id, Long memberId, Long courseId, EnrollmentStatus status) {
        return new Enrollment(id, memberId, courseId, status);
    }

    public void cancel() {
        if (this.status == EnrollmentStatus.CANCELED) {
            throw new EnrollmentAlreadyCanceledException();
        }

        this.status = EnrollmentStatus.CANCELED;
    }

    public void complete() {
        // TODO: 수강 완료 상태 전이
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }
}
