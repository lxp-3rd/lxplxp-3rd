package com.ohgiraffers.lxp.announcement.presentation.dto;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;

import java.time.LocalDateTime;

public record AnnouncementResponse(
        Long id,
        String title,
        String content,
        AnnouncementStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AnnouncementResponse from(AnnouncementResult result) {
        return new AnnouncementResponse(
                result.id(),
                result.title(),
                result.content(),
                result.status(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}