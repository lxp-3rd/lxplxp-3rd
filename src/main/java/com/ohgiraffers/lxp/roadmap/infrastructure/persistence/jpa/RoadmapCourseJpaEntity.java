package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "roadmap_courses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadmapCourseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private RoadmapJpaEntity roadmap;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private Integer sequence;

    public RoadmapCourseJpaEntity(RoadmapJpaEntity roadmap, Long courseId, Integer sequence) {
        this.roadmap = roadmap;
        this.courseId = courseId;
        this.sequence = sequence;
    }
}
