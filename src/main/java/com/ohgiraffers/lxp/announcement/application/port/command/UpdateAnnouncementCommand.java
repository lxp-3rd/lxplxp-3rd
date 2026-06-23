package com.ohgiraffers.lxp.announcement.application.port.command;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;

public record UpdateAnnouncementCommand(
        Long id,
        String title,
        String content,
        AnnouncementStatus status
) {
}
