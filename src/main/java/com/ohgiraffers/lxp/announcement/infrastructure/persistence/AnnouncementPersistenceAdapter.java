package com.ohgiraffers.lxp.announcement.infrastructure.persistence;

import com.ohgiraffers.lxp.announcement.application.port.out.SaveAnnouncementPort;
import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementJpaEntity;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnnouncementPersistenceAdapter implements SaveAnnouncementPort {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementPersistenceAdapter(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    @Transactional
    public Announcement save(Announcement announcement) {
        AnnouncementJpaEntity entity = AnnouncementMapper.toJpaEntity(announcement);
        AnnouncementJpaEntity saved = announcementRepository.save(entity);
        return AnnouncementMapper.toDomain(saved);
    }
}