package com.ohgiraffers.lxp.announcement.domain.model.entity;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

import java.time.LocalDateTime;

public class Announcement {

    private static final int MINIMUM_TITLE_LENGTH = 2;
    private static final int MAXIMUM_TITLE_LENGTH = 100;

    private final Long id;
    private final Long adminId;
    private String title;
    private String content;
    private AnnouncementStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Announcement(Long id, Long adminId, String title, String content,
            AnnouncementStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        validate(title, content);
        this.id = id;
        this.adminId = adminId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static Announcement create(Long adminId, String title, String content, AnnouncementStatus status) {
        return new Announcement(null, adminId, title, content, status, null, null);
    }


    public static Announcement restore(Long id, Long adminId, String title, String content,
            AnnouncementStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Announcement(id, adminId, title, content, status, createdAt, updatedAt);
    }

    public void updateContent(String title, String content) {
        validate(title, content);
        this.title = title;
        this.content = content;
    }

    private static void validate(String title, String content) {
        if (title == null || content == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        validateTitle(title);
        validateContent(content);
    }

    private static void validateTitle(String title) {
        if (title.isBlank() || title.length() < MINIMUM_TITLE_LENGTH || title.length() > MAXIMUM_TITLE_LENGTH) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }

    private static void validateContent(String content) {
        if (content.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }

    public void changeStatus(AnnouncementStatus status) {
        this.status = status;
    }

    public boolean isPublished() {
        return AnnouncementStatus.PUBLISH == this.status;
    }

    public Long getId() { return id; }
    public Long getAdminId() { return adminId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public AnnouncementStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}