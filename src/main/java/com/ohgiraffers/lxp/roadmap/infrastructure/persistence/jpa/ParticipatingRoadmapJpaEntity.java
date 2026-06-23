package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;
import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "participating_roadmaps")
public class ParticipatingRoadmapJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long roadmapId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipatingRoadmapStatus status;

    protected ParticipatingRoadmapJpaEntity() {
    }

    public ParticipatingRoadmapJpaEntity(Long memberId, Long roadmapId, ParticipatingRoadmapStatus status) {
        this.memberId = memberId;
        this.roadmapId = roadmapId;
        this.status = status;
    }

    public ParticipatingRoadmap toDomain() {
        return ParticipatingRoadmap.restore(id, memberId, roadmapId, status);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public ParticipatingRoadmapStatus getStatus() {
        return status;
    }
}
