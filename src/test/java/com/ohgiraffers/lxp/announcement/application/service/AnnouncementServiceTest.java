package com.ohgiraffers.lxp.announcement.application.service;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.command.UpdateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.out.DeleteAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.LoadAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.SaveAnnouncementPort;
import com.ohgiraffers.lxp.announcement.application.port.out.UpdateAnnouncementPort;
import com.ohgiraffers.lxp.announcement.domain.model.entity.Announcement;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {

    @InjectMocks
    private AnnouncementService announcementService;

    @Mock
    private SaveAnnouncementPort saveAnnouncementPort;

    @Mock
    private LoadAnnouncementPort loadAnnouncementPort;

    @Mock
    private UpdateAnnouncementPort updateAnnouncementPort;

    @Mock
    private DeleteAnnouncementPort deleteAnnouncementPort;

    @DisplayName("공지사항 등록 시 저장 포트가 호출되고 결과를 반환한다.")
    @Test
    void createAnnouncement() {
        CreateAnnouncementCommand command = new CreateAnnouncementCommand(
                1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH
        );
        Announcement saved = Announcement.restore(
                1L, 1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH, null, null
        );
        given(saveAnnouncementPort.save(any())).willReturn(saved);

        AnnouncementResult result = announcementService.createAnnouncement(command);

        assertThat(result.id()).isEqualTo(1L);
        then(saveAnnouncementPort).should().save(any());
    }

    @DisplayName("공지사항 수정 시 수정된 결과를 반환한다.")
    @Test
    void updateAnnouncement() {
        UpdateAnnouncementCommand command = new UpdateAnnouncementCommand(
                1L, "수정된 제목", "수정된 내용", AnnouncementStatus.HIDDEN
        );
        Announcement loaded = Announcement.restore(
                1L, 1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH, null, null
        );
        Announcement updated = Announcement.restore(
                1L, 1L, "수정된 제목", "수정된 내용", AnnouncementStatus.HIDDEN, null, null
        );
        given(loadAnnouncementPort.findById(1L)).willReturn(Optional.of(loaded));
        given(updateAnnouncementPort.update(any())).willReturn(updated);

        AnnouncementResult result = announcementService.updateAnnouncement(command);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("수정된 제목");
        assertThat(result.status()).isEqualTo(AnnouncementStatus.HIDDEN);
    }

    @DisplayName("존재하지 않는 공지사항 수정 시 예외가 발생한다.")
    @Test
    void updateAnnouncement_notFound() {
        UpdateAnnouncementCommand command = new UpdateAnnouncementCommand(
                999L, "수정된 제목", "수정된 내용", AnnouncementStatus.HIDDEN
        );
        given(loadAnnouncementPort.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> announcementService.updateAnnouncement(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.ANNOUNCEMENT_NOT_FOUND.getMessage());
    }

    @DisplayName("공지사항 삭제 시 삭제 포트가 호출되고 id를 반환한다.")
    @Test
    void deleteAnnouncement() {
        Announcement loaded = Announcement.restore(
                1L, 1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH, null, null
        );
        given(loadAnnouncementPort.findById(1L)).willReturn(Optional.of(loaded));

        Long id = announcementService.deleteAnnouncement(1L);

        assertThat(id).isEqualTo(1L);
        then(deleteAnnouncementPort).should().delete(1L);
    }

    @DisplayName("존재하지 않는 공지사항 삭제 시 예외가 발생한다.")
    @Test
    void deleteAnnouncement_notFound() {
        given(loadAnnouncementPort.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> announcementService.deleteAnnouncement(999L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.ANNOUNCEMENT_NOT_FOUND.getMessage());
    }
}