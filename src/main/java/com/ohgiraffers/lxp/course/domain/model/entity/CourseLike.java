package com.ohgiraffers.lxp.course.domain.model.entity;

import java.time.LocalDateTime;

public class CourseLike {

    private final Long id;
    private final Long courseId;
    private final Long learnerId;
    private final LocalDateTime createdAt;

    private CourseLike(Long id, Long courseId, Long learnerId, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.learnerId = learnerId;
        this.createdAt = createdAt;
    }

    public static CourseLike create(Long courseId, Long learnerId) {
        if (courseId == null) throw new IllegalArgumentException("강좌 ID는 필수입니다.");
        if (learnerId == null) throw new IllegalArgumentException("수강생 ID는 필수입니다.");
        return new CourseLike(null, courseId, learnerId, null);
    }

    public static CourseLike restore(Long id, Long courseId, Long learnerId, LocalDateTime createdAt) {
        return new CourseLike(id, courseId, learnerId, createdAt);
    }

    public Long getId() { return id; }
    public Long getCourseId() { return courseId; }
    public Long getLearnerId() { return learnerId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
