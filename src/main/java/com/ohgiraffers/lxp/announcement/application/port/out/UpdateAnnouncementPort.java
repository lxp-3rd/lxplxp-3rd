package com.ohgiraffers.lxp.announcement.application.port.out;

import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;

public interface UpdateAnnouncementPort {
    Announcement update(Announcement announcement);
}