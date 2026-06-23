package com.ohgiraffers.lxp.announcement.application.port.out;

import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;

public interface SaveAnnouncementPort {

    Announcement save(Announcement announcement);
}