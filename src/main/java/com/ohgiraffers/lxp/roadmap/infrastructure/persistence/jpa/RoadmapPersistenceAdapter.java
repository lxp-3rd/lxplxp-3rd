package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapRepositoryPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;
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
    public List<Roadmap> findAll() {
        return roadmapJpaRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc()
                .stream()
                .map(RoadmapJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Roadmap> findById(Long roadmapId) {
        return roadmapJpaRepository.findByIdAndDeletedAtIsNull(roadmapId)
                .map(RoadmapJpaEntity::toDomain);
    }

    @Override
    public List<Roadmap> findAllAvailable(Long memberId, List<ParticipatingRoadmap> participatingRoadmaps) {
        List<Long> participatingRoadmapIds = toRoadmapIds(participatingRoadmaps);
        if (participatingRoadmapIds.isEmpty()) {
            return roadmapJpaRepository.findAllByMemberIdNotAndDeletedAtIsNullOrderByCreatedAtDesc(memberId)
                    .stream()
                    .map(RoadmapJpaEntity::toDomain)
                    .toList();
        }

        return roadmapJpaRepository.findAllByMemberIdNotAndIdNotInAndDeletedAtIsNullOrderByCreatedAtDesc(
                        memberId,
                        participatingRoadmapIds
                )
                .stream()
                .map(RoadmapJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Roadmap> findAllByParticipatingRoadmaps(List<ParticipatingRoadmap> participatingRoadmaps) {
        List<Long> roadmapIds = toRoadmapIds(participatingRoadmaps);
        if (roadmapIds.isEmpty()) {
            return List.of();
        }

        return roadmapJpaRepository.findAllByIdInAndDeletedAtIsNullOrderByCreatedAtDesc(roadmapIds)
                .stream()
                .map(RoadmapJpaEntity::toDomain)
                .toList();
    }

    private List<Long> toRoadmapIds(List<ParticipatingRoadmap> participatingRoadmaps) {
        return participatingRoadmaps.stream()
                .map(ParticipatingRoadmap::getRoadmapId)
                .toList();
    }

    @Override
    public List<Roadmap> findAllCreatedByMemberId(Long memberId) {
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
