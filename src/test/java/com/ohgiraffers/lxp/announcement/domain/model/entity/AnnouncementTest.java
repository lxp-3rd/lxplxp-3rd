package com.ohgiraffers.lxp.announcement.domain.model.entity;

import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnnouncementTest {

    @DisplayName("공지사항을 생성하면 필드가 올바르게 세팅된다.")
    @Test
    void create() {
        Announcement announcement = Announcement.create(1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH);

        assertThat(announcement.getId()).isNull();
        assertThat(announcement.getAdminId()).isEqualTo(1L);
        assertThat(announcement.getTitle()).isEqualTo("6월 22일 공지사항");
        assertThat(announcement.getContent()).isEqualTo("6월 22일 공지사항입니다.");
        assertThat(announcement.getStatus()).isEqualTo(AnnouncementStatus.PUBLISH);
    }

    @DisplayName("PUBLISH 상태이면 isPublished()가 true를 반환한다.")
    @Test
    void isPublished_publish() {
        Announcement announcement = Announcement.create(1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH);

        assertThat(announcement.isPublished()).isTrue();
    }

    @DisplayName("HIDDEN 상태이면 isPublished()가 false를 반환한다.")
    @Test
    void isPublished_hidden() {
        Announcement announcement = Announcement.create(1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.HIDDEN);

        assertThat(announcement.isPublished()).isFalse();
    }
}