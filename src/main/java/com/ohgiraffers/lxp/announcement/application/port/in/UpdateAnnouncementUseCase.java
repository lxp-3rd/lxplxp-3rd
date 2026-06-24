package com.ohgiraffers.lxp.announcement.application.port.in;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.command.UpdateAnnouncementCommand;

public interface UpdateAnnouncementUseCase {
    AnnouncementResult updateAnnouncement(UpdateAnnouncementCommand command);
}