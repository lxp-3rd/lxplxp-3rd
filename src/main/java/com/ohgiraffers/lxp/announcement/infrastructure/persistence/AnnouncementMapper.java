package com.ohgiraffers.lxp.announcement.infrastructure.persistence;

import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementJpaEntity;

final class AnnouncementMapper {

    private AnnouncementMapper() {}

    static AnnouncementJpaEntity toJpaEntity(Announcement announcement) {
        return new AnnouncementJpaEntity(
                announcement.getAdminId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getStatus()
        );
    }

    static Announcement toDomain(AnnouncementJpaEntity entity) {
        return Announcement.restore(
                entity.getId(),
                entity.getAdminId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}