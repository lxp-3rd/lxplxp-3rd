package com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import com.ohgiraffers.lxp.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "announcements")
public class AnnouncementJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long adminId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnouncementStatus status;

    protected AnnouncementJpaEntity() {}

    public AnnouncementJpaEntity(Long adminId, String title, String content, AnnouncementStatus status) {
        this.adminId = adminId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public void update(String title, String content, AnnouncementStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getAdminId() { return adminId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public AnnouncementStatus getStatus() { return status; }
}
