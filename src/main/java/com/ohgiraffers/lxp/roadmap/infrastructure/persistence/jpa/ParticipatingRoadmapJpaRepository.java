package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipatingRoadmapJpaRepository extends JpaRepository<ParticipatingRoadmapJpaEntity, Long> {

    List<ParticipatingRoadmapJpaEntity> findAllByMemberIdAndStatusAndDeletedAtIsNull(
            Long memberId,
            ParticipatingRoadmapStatus status
    );

    boolean existsByMemberIdAndRoadmapIdAndDeletedAtIsNull(Long memberId, Long roadmapId);
}
