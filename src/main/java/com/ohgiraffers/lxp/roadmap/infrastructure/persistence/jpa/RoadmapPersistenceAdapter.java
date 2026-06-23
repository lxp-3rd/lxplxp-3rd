package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapRepositoryPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class RoadmapPersistenceAdapter implements RoadmapRepositoryPort {

    private final RoadmapJpaRepository roadmapJpaRepository;

    public RoadmapPersistenceAdapter(RoadmapJpaRepository roadmapJpaRepository) {
        this.roadmapJpaRepository = roadmapJpaRepository;
    }

    @Override
    @Transactional
    public Roadmap save(Roadmap roadmap) {
        if (roadmap.getId() == null) {
            return roadmapJpaRepository.save(RoadmapJpaEntity.from(roadmap)).toDomain();
        }

        RoadmapJpaEntity entity = roadmapJpaRepository.findByIdAndDeletedAtIsNull(roadmap.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ROADMAP_NOT_FOUND));
        entity.update(roadmap.getName(), roadmap.getIntroduction(), roadmap.getCourseIds());
        return entity.toDomain();
    }

    @Override
    public Optional<Roadmap> findById(Long roadmapId) {
        return roadmapJpaRepository.findByIdAndDeletedAtIsNull(roadmapId)
                .map(RoadmapJpaEntity::toDomain);
    }

    @Override
    public List<Roadmap> findAllByMemberId(Long memberId) {
        return roadmapJpaRepository.findAllByMemberIdAndDeletedAtIsNullOrderByCreatedAtDesc(memberId)
                .stream()
                .map(RoadmapJpaEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long roadmapId) {
        RoadmapJpaEntity entity = roadmapJpaRepository.findByIdAndDeletedAtIsNull(roadmapId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROADMAP_NOT_FOUND));
        entity.delete();
    }
}
