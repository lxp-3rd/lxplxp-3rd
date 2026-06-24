package com.ohgiraffers.lxp.content.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "content")
public class ContentJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "sequence", nullable = false)
    private int sequence;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content_url", nullable = false, columnDefinition = "TEXT")
    private String contentUrl;

    protected ContentJpaEntity() {}

    public static ContentJpaEntity from(Content domain) {
        ContentJpaEntity entity = new ContentJpaEntity();
        entity.id = domain.getId();
        entity.courseId = domain.getCourseId();
        entity.sequence = domain.getSequence();
        entity.title = domain.getTitle();
        entity.contentUrl = domain.getContentUrl();
        return entity;
    }

    public Long getId() { return id; }
    public Long getCourseId() { return courseId; }
    public int getSequence() { return sequence; }
    public String getTitle() { return title; }
    public String getContentUrl() { return contentUrl; }

    public Content toDomain() {
        return Content.restore(id, courseId, sequence, title, contentUrl);
    }
}
