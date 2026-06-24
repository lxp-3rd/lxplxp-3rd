package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapParticipationPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;
import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParticipatingRoadmapPersistenceAdapter implements RoadmapParticipationPort {

    private final ParticipatingRoadmapJpaRepository participatingRoadmapJpaRepository;

    public ParticipatingRoadmapPersistenceAdapter(
            ParticipatingRoadmapJpaRepository participatingRoadmapJpaRepository
    ) {
        this.participatingRoadmapJpaRepository = participatingRoadmapJpaRepository;
    }

    @Override
    public List<ParticipatingRoadmap> findAllByMemberId(Long memberId) {
        return participatingRoadmapJpaRepository.findAllByMemberIdAndStatusAndDeletedAtIsNull(
                        memberId,
                        ParticipatingRoadmapStatus.ACTIVE
                )
                .stream()
                .map(ParticipatingRoadmapJpaEntity::toDomain)
                .toList();
    }

    @Override
    public boolean existsByMemberIdAndRoadmapId(Long memberId, Long roadmapId) {
        return participatingRoadmapJpaRepository.existsByMemberIdAndRoadmapIdAndDeletedAtIsNull(memberId, roadmapId);
    }

    @Override
    public ParticipatingRoadmap participate(Long memberId, Long roadmapId) {
        ParticipatingRoadmap domain = ParticipatingRoadmap.participate(memberId, roadmapId);
        return participatingRoadmapJpaRepository.save(ParticipatingRoadmapJpaEntity.from(domain)).toDomain();
    }
}
