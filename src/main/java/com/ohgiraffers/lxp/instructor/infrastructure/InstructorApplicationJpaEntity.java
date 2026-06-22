package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "instructor_application",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_instructor_application_pending_lock",
                columnNames = "pending_lock"
        )
)
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

    /**
     * PENDING 상태일 때만 memberId 값을 가지며, 그 외에는 NULL.
     * NULL은 유니크 제약 대상에서 제외되므로 PENDING 상태의 중복만 DB 레벨에서 차단.
     */
    @Column(name = "pending_lock", unique = true)
    private Long pendingLock;

    protected InstructorApplicationJpaEntity() {
    }

    public static InstructorApplicationJpaEntity from(InstructorApplication domain) {
        InstructorApplicationJpaEntity entity = new InstructorApplicationJpaEntity();
        entity.id = domain.getId();
        entity.memberId = domain.getMemberId();
        entity.instructorName = domain.getInstructorName();
        entity.introduction = domain.getIntroduction();
        entity.expertise = domain.getExpertise();
        entity.status = domain.getStatus();
        entity.rejectionReason = domain.getRejectionReason();
        entity.resolvedAt = domain.getResolvedAt();
        entity.pendingLock = domain.getStatus() == ApplicationStatus.PENDING
                ? domain.getMemberId()
                : null;
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
}
