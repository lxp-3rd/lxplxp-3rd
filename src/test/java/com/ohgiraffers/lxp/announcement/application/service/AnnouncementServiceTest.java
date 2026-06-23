package com.ohgiraffers.lxp.announcement.application.service;

import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.out.SaveAnnouncementPort;
import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {

    @InjectMocks
    private AnnouncementService announcementService;

    @Mock
    private SaveAnnouncementPort saveAnnouncementPort;

    @DisplayName("공지사항 등록 시 저장 포트가 호출되고 id를 반환한다.")
    @Test
    void createAnnouncement() {
        CreateAnnouncementCommand command = new CreateAnnouncementCommand(
                1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH
        );
        Announcement saved = Announcement.restore(
                1L, 1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH, null, null
        );
        given(saveAnnouncementPort.save(any())).willReturn(saved);

        Long id = announcementService.createAnnouncement(command);

        assertThat(id).isEqualTo(1L);
        then(saveAnnouncementPort).should().save(any());
    }
}