package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Entity
@Table(name = "roadmaps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadmapJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 200)
    private String introduction;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoadmapCourseJpaEntity> courses = new ArrayList<>();

    private RoadmapJpaEntity(Long memberId, String name, String introduction) {
        this.memberId = memberId;
        this.name = name;
        this.introduction = introduction;
    }

    public static RoadmapJpaEntity from(Roadmap roadmap) {
        RoadmapJpaEntity entity = new RoadmapJpaEntity(
                roadmap.getMemberId(),
                roadmap.getName(),
                roadmap.getIntroduction()
        );
        entity.replaceCourses(roadmap.getCourseIds());
        return entity;
    }

    public void update(String name, String introduction, List<Long> courseIds) {
        this.name = name;
        this.introduction = introduction;
        replaceCourses(courseIds);
    }

    private void replaceCourses(List<Long> courseIds) {
        this.courses.clear();
        for (int i = 0; i < courseIds.size(); i++) {
            this.courses.add(new RoadmapCourseJpaEntity(this, courseIds.get(i), i + 1));
        }
    }

    public Roadmap toDomain() {
        return Roadmap.restore(
                id,
                memberId,
                name,
                introduction,
                courses.stream()
                        .sorted(Comparator.comparing(RoadmapCourseJpaEntity::getSequence))
                        .map(RoadmapCourseJpaEntity::getCourseId)
                        .toList(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }
}
