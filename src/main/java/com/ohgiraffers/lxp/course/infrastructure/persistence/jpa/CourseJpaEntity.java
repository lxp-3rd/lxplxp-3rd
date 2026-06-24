package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;
import com.ohgiraffers.lxp.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class CourseJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "hidden_by")
    private HiddenBy hiddenBy;

    protected CourseJpaEntity() {
    }

    public static CourseJpaEntity from(Course domain) {
        CourseJpaEntity entity = new CourseJpaEntity();
        entity.id = domain.getId();
        entity.instructorId = domain.getInstructorId();
        entity.title = domain.getTitle();
        entity.description = domain.getDescription();
        entity.thumbnailUrl = domain.getThumbnailUrl();
        entity.status = domain.getStatus();
        entity.hiddenBy = domain.getHiddenBy();
        return entity;
    }

    public Long getId() {
        return id;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Course toDomain() {
        return Course.restore(id, instructorId, title, description, thumbnailUrl, status, hiddenBy);
    }
}
