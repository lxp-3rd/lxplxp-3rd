package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "instructor_application")
public class InstructorApplicationJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "instructor_name", nullable = false, length = 10)
    private String instructorName;

    @Column(name = "introduction", nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "expertise", nullable = false, length = 100)
    private String expertise;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "rejection_reason", length = 200)
    private String rejectionReason;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    protected InstructorApplicationJpaEntity() {
    }

    public static InstructorApplicationJpaEntity from(InstructorApplication domain) {
        InstructorApplicationJpaEntity entity = new InstructorApplicationJpaEntity();
        entity.memberId = domain.getMemberId();
        entity.instructorName = domain.getInstructorName();
        entity.introduction = domain.getIntroduction();
        entity.expertise = domain.getExpertise();
        entity.status = domain.getStatus();
        entity.rejectionReason = domain.getRejectionReason();
        entity.resolvedAt = domain.getResolvedAt();
        return entity;
    }

    public InstructorApplication toDomain() {
        return InstructorApplication.reconstitute(
                id,
                memberId,
                instructorName,
                introduction,
                expertise,
                status,
                rejectionReason,
                getCreatedAt(),
                resolvedAt
        );
    }

    public boolean hasMemberIdAndStatus(Long memberId, ApplicationStatus status) {
        return this.memberId.equals(memberId) && this.status == status;
    }
}
