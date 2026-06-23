package com.ohgiraffers.lxp.announcement.application.port.in;

import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;

public interface CreateAnnouncementUseCase {

    Long createAnnouncement(CreateAnnouncementCommand command);
}