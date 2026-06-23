package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;
import jakarta.persistence.*;

@Entity
@Table(
        name = "instructor_profile",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_instructor_profile_instructor_id",
                columnNames = "instructor_id"
        )
)
public class InstructorProfileJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "bio", nullable = false, columnDefinition = "TEXT")
    private String bio;

    protected InstructorProfileJpaEntity() {
    }

    public static InstructorProfileJpaEntity from(InstructorProfile domain) {
        InstructorProfileJpaEntity entity = new InstructorProfileJpaEntity();
        entity.id = domain.getId();
        entity.instructorId = domain.getInstructorId();
        entity.profileImageUrl = domain.getProfileImageUrl();
        entity.bio = domain.getBio();
        return entity;
    }

    public InstructorProfile toDomain() {
        return InstructorProfile.restore(id, instructorId, profileImageUrl, bio);
    }
}
