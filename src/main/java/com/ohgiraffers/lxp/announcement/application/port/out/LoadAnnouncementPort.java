package com.ohgiraffers.lxp.announcement.application.port.out;

import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;

import java.util.List;
import java.util.Optional;

public interface LoadAnnouncementPort {
    Optional<Announcement> findById(Long id);
    List<Announcement> findAll();
}