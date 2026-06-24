package com.ohgiraffers.lxp.announcement.application.service;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.command.UpdateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.DeleteAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.UpdateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.out.DeleteAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.LoadAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.SaveAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.UpdateAnnouncementPort;
import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnnouncementService implements CreateAnnouncementUseCase, UpdateAnnouncementUseCase, DeleteAnnouncementUseCase {

    private final SaveAnnouncementPort saveAnnouncementPort;
    private final LoadAnnouncementPort loadAnnouncementPort;
    private final UpdateAnnouncementPort updateAnnouncementPort;
    private final DeleteAnnouncementPort deleteAnnouncementPort;

    public AnnouncementService(SaveAnnouncementPort saveAnnouncementPort,
                               LoadAnnouncementPort loadAnnouncementPort,
                               UpdateAnnouncementPort updateAnnouncementPort,
                               DeleteAnnouncementPort deleteAnnouncementPort) {
        this.saveAnnouncementPort = saveAnnouncementPort;
        this.loadAnnouncementPort = loadAnnouncementPort;
        this.updateAnnouncementPort = updateAnnouncementPort;
        this.deleteAnnouncementPort = deleteAnnouncementPort;
    }

    @Override
    public AnnouncementResult createAnnouncement(CreateAnnouncementCommand command) {
        Announcement announcement = Announcement.create(
                command.adminId(),
                command.title(),
                command.content(),
                command.status()
        );
        Announcement saved = saveAnnouncementPort.save(announcement);
        return AnnouncementResult.from(saved);
    }

    @Override
    public AnnouncementResult updateAnnouncement(UpdateAnnouncementCommand command) {
        Announcement announcement = loadAnnouncementPort.findById(command.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
        announcement.updateContent(command.title(), command.content());
        announcement.changeStatus(command.status());
        Announcement updated = updateAnnouncementPort.update(announcement);
        return AnnouncementResult.from(updated);
    }

    @Override
    public Long deleteAnnouncement(Long id) {
        loadAnnouncementPort.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
        deleteAnnouncementPort.delete(id);
        return id;
    }
}