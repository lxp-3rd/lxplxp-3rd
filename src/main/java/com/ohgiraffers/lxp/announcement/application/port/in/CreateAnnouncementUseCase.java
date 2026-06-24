package com.ohgiraffers.lxp.announcement.application.port.in;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;

public interface CreateAnnouncementUseCase {

    AnnouncementResult createAnnouncement(CreateAnnouncementCommand command);
}