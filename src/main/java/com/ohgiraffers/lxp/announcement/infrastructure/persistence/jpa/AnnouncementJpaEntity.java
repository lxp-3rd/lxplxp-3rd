package com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@EntityListeners(AuditingEntityListener.class)
public class AnnouncementJpaEntity {

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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected AnnouncementJpaEntity() {}

    public AnnouncementJpaEntity(Long adminId, String title, String content, AnnouncementStatus status) {
        this.adminId = adminId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getAdminId() { return adminId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public AnnouncementStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
