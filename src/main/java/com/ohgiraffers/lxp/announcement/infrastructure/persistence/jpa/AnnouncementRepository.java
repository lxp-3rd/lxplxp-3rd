package com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<AnnouncementJpaEntity, Long> {
}