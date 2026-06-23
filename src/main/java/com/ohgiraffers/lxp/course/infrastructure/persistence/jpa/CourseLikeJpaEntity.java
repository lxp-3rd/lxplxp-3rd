package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "course_like",
        uniqueConstraints = @UniqueConstraint(name = "uq_course_like_course_learner", columnNames = {"course_id", "learner_id"})
)
@EntityListeners(AuditingEntityListener.class)
public class CourseLikeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "learner_id", nullable = false)
    private Long learnerId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected CourseLikeJpaEntity() {}

    public static CourseLikeJpaEntity from(CourseLike domain) {
        CourseLikeJpaEntity entity = new CourseLikeJpaEntity();
        entity.id = domain.getId();
        entity.courseId = domain.getCourseId();
        entity.learnerId = domain.getLearnerId();
        return entity;
    }

    public Long getId() { return id; }

    public CourseLike toDomain() {
        return CourseLike.restore(id, courseId, learnerId, createdAt);
    }
}
