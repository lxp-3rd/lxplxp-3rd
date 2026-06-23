package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.instructor.domain.Instructor;
import com.ohgiraffers.lxp.instructor.domain.InstructorStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "instructor")
public class InstructorJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InstructorStatus status;

    protected InstructorJpaEntity() {
    }

    public static InstructorJpaEntity from(Instructor domain) {
        InstructorJpaEntity entity = new InstructorJpaEntity();
        entity.id = domain.getId();
        entity.memberId = domain.getMemberId();
        entity.status = domain.getStatus();
        return entity;
    }

    public Instructor toDomain() {
        return Instructor.restore(id, memberId, status);
    }
}
