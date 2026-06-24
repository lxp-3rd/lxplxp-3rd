package com.ohgiraffers.lxp.announcement.infrastructure.persistence;

import com.ohgiraffers.lxp.announcement.application.port.out.DeleteAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.LoadAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.SaveAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.UpdateAnnouncementPort;
import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementJpaEntity;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementRepository;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class AnnouncementPersistenceAdapter implements SaveAnnouncementPort, LoadAnnouncementPort, UpdateAnnouncementPort, DeleteAnnouncementPort {

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

    @Override
    public Optional<Announcement> findById(Long id) {
        return announcementRepository.findById(id)
                .map(AnnouncementMapper::toDomain);
    }

    @Override
    public List<Announcement> findAll() {
        return announcementRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(AnnouncementMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Announcement update(Announcement announcement) {
        AnnouncementJpaEntity entity = announcementRepository.findById(announcement.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
        entity.update(announcement.getTitle(), announcement.getContent(), announcement.getStatus());
        return AnnouncementMapper.toDomain(announcementRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}