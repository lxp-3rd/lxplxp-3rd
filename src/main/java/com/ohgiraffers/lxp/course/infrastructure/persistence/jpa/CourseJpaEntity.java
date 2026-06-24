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

    // 도메인 Course.description(짧은 소개)은 summary 컬럼에 매핑된다.
    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    // 상세 설명. 생성/수정 도메인이 아직 다루지 않는 읽기 전용 필드라 nullable.
    @Column(name = "description", columnDefinition = "TEXT")
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
        entity.summary = domain.getDescription();
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

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Course toDomain() {
        return Course.restore(id, instructorId, title, summary, thumbnailUrl, status, hiddenBy);
    }
}
