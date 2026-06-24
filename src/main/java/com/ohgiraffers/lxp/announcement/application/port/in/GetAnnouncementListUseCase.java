package com.ohgiraffers.lxp.announcement.application.port.in;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;

import java.util.List;

public interface GetAnnouncementListUseCase {
    List<AnnouncementResult> getAnnouncements();
}
