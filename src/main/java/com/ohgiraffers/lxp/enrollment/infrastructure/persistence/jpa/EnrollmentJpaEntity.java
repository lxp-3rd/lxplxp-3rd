package com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollment")
public class EnrollmentJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long courseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    protected EnrollmentJpaEntity() {
    }

    public EnrollmentJpaEntity(Long memberId, Long courseId, EnrollmentStatus status) {
        this.memberId = memberId;
        this.courseId = courseId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    /**
     * 도메인에서 전이된 상태를 영속 엔티티에 반영한다.
     * CANCELED로 전이된 경우 soft delete(deletedAt)를 함께 기록한다.
     */
    public void applyStatus(EnrollmentStatus status) {
        this.status = status;
        if (status == EnrollmentStatus.CANCELED) {
            delete();
        }
    }
}
