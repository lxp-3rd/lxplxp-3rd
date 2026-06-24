package com.ohgiraffers.lxp.announcement.application.port.in;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;

public interface GetAnnouncementUseCase {
    AnnouncementResult getAnnouncement(Long id);
}
