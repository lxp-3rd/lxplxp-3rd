package com.ohgiraffers.lxp.roadmap.domain.model.entity;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Roadmap {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MIN_INTRODUCTION_LENGTH = 20;
    private static final int MAX_INTRODUCTION_LENGTH = 200;
    private static final int MIN_COURSE_COUNT = 2;

    private final Long id;
    private final Long memberId;
    private final String name;
    private final String introduction;
    private final List<Long> courseIds;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Roadmap(Long id, Long memberId, String name, String introduction, List<Long> courseIds,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        requireNonNull(memberId, "memberId");
        validate(name, introduction, courseIds);
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.introduction = introduction;
        this.courseIds = List.copyOf(courseIds);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Roadmap create(Long memberId, String name, String introduction, List<Long> courseIds) {
        return new Roadmap(null, memberId, name, introduction, courseIds, null, null);
    }

    public static Roadmap restore(Long id, Long memberId, String name, String introduction, List<Long> courseIds,
                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Roadmap(id, memberId, name, introduction, courseIds, createdAt, updatedAt);
    }

    public Roadmap update(String name, String introduction, List<Long> courseIds) {
        return new Roadmap(id, memberId, name, introduction, courseIds, createdAt, updatedAt);
    }

    public boolean isOwnedBy(Long memberId) {
        return this.memberId.equals(memberId);
    }

    private void validate(String name, String introduction, List<Long> courseIds) {
        requireLength(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH, "name");
        requireLength(introduction, MIN_INTRODUCTION_LENGTH, MAX_INTRODUCTION_LENGTH, "introduction");
        requireCourseIds(courseIds);
    }

    private void requireLength(String value, int min, int max, String field) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        int length = value.trim().length();
        if (length < min || length > max) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }

    private void requireCourseIds(List<Long> courseIds) {
        if (courseIds == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        if (courseIds.stream().anyMatch(Objects::isNull)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        if (courseIds.stream().distinct().count() != courseIds.size()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        if (courseIds.size() < MIN_COURSE_COUNT) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }

    private void requireNonNull(Object value, String field) {
        if (value == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
