package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoadmapJpaRepository extends JpaRepository<RoadmapJpaEntity, Long> {

    @EntityGraph(attributePaths = "courses")
    Optional<RoadmapJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = "courses")
    List<RoadmapJpaEntity> findAllByMemberIdAndDeletedAtIsNullOrderByCreatedAtDesc(Long memberId);

    @EntityGraph(attributePaths = "courses")
    List<RoadmapJpaEntity> findAllByMemberIdNotAndDeletedAtIsNullOrderByCreatedAtDesc(Long memberId);

    @EntityGraph(attributePaths = "courses")
    List<RoadmapJpaEntity> findAllByMemberIdNotAndIdNotInAndDeletedAtIsNullOrderByCreatedAtDesc(
            Long memberId,
            Collection<Long> roadmapIds
    );

    @EntityGraph(attributePaths = "courses")
    List<RoadmapJpaEntity> findAllByIdInAndDeletedAtIsNullOrderByCreatedAtDesc(Collection<Long> roadmapIds);
}
