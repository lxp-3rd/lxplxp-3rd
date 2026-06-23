package com.ohgiraffers.lxp.announcement.domain.model.entity;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;

import java.time.LocalDateTime;

public class Announcement {

    private final Long id;
    private final Long adminId;
    private String title;
    private String content;
    private AnnouncementStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Announcement(Long id, Long adminId, String title, String content,
            AnnouncementStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        this.title = title;
        this.content = content;
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