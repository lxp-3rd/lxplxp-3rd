package com.ohgiraffers.lxp.announcement.application.dto;

import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;

import java.time.LocalDateTime;

public record AnnouncementResult(
        Long id,
        String title,
        String content,
        AnnouncementStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AnnouncementResult from(Announcement announcement) {
        return new AnnouncementResult(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getStatus(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt()
        );
    }
}
