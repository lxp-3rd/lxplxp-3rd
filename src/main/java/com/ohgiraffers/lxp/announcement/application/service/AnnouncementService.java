package com.ohgiraffers.lxp.announcement.application.service;

import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.out.SaveAnnouncementPort;
import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnnouncementService implements CreateAnnouncementUseCase {

    private final SaveAnnouncementPort saveAnnouncementPort;

    public AnnouncementService(SaveAnnouncementPort saveAnnouncementPort) {
        this.saveAnnouncementPort = saveAnnouncementPort;
    }

    @Override
    public Long createAnnouncement(CreateAnnouncementCommand command) {
        Announcement announcement = Announcement.create(
                command.adminId(),
                command.title(),
                command.content(),
                command.status()
        );
        Announcement saved = saveAnnouncementPort.save(announcement);
        return saved.getId();
    }
}