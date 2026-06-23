package com.ohgiraffers.lxp.announcement.presentation.web;
import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.DeleteAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.UpdateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnouncementController.class)
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateAnnouncementUseCase createAnnouncementUseCase;

    @MockitoBean
    private UpdateAnnouncementUseCase updateAnnouncementUseCase;

    @MockitoBean
    private DeleteAnnouncementUseCase deleteAnnouncementUseCase;

    @DisplayName("정상 요청 시 공지사항이 등록되고 201을 반환한다.")
    @Test
    void create() throws Exception {
        AnnouncementResult result = new AnnouncementResult(
                1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH,
                LocalDateTime.now(), LocalDateTime.now()
        );
        given(createAnnouncementUseCase.createAnnouncement(any())).willReturn(result);

        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "adminId": 1,
                                    "title": "6월 22일 공지사항",
                                    "content": "6월 22일 공지사항입니다.",
                                    "status": "PUBLISH"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("6월 22일 공지사항"))
                .andExpect(jsonPath("$.status").value("PUBLISH"));
    }

    @DisplayName("adminId가 없으면 400을 반환한다.")
    @Test
    void create_adminIdIsNull() throws Exception {
        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "6월 22일 공지사항",
                                    "content": "6월 22일 공지사항입니다.",
                                    "status": "PUBLISH"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("title이 없으면 400을 반환한다.")
    @Test
    void create_titleIsBlank() throws Exception {
        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "adminId": 1,
                                    "content": "6월 22일 공지사항입니다.",
                                    "status": "PUBLISH"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("title이 2자 미만이면 400을 반환한다.")
    @Test
    void create_titleTooShort() throws Exception {
        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "adminId": 1,
                                    "title": "공",
                                    "content": "6월 22일 공지사항입니다.",
                                    "status": "PUBLISH"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("title이 100자 초과이면 400을 반환한다.")
    @Test
    void create_titleTooLong() throws Exception {
        String longTitle = "공".repeat(101);

        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "adminId": 1,
                                    "title": "%s",
                                    "content": "6월 22일 공지사항입니다.",
                                    "status": "PUBLISH"
                                }
                                """.formatted(longTitle)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("content가 없으면 400을 반환한다.")
    @Test
    void create_contentIsBlank() throws Exception {
        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "adminId": 1,
                                    "title": "6월 22일 공지사항",
                                    "status": "PUBLISH"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("status가 없으면 400을 반환한다.")
    @Test
    void create_statusIsNull() throws Exception {
        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "adminId": 1,
                                    "title": "6월 22일 공지사항",
                                    "content": "6월 22일 공지사항입니다."
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("정상 요청 시 공지사항이 수정되고 200을 반환한다.")
    @Test
    void update() throws Exception {
        AnnouncementResult result = new AnnouncementResult(
                1L, "수정된 제목", "수정된 내용", AnnouncementStatus.HIDDEN,
                LocalDateTime.now(), LocalDateTime.now()
        );
        given(updateAnnouncementUseCase.updateAnnouncement(any())).willReturn(result);

        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "수정된 제목",
                                    "content": "수정된 내용",
                                    "status": "HIDDEN"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.status").value("HIDDEN"));
    }

    @DisplayName("수정 시 title이 없으면 400을 반환한다.")
    @Test
    void update_titleIsBlank() throws Exception {
        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "content": "수정된 내용",
                                    "status": "HIDDEN"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("수정 시 title이 2자 미만이면 400을 반환한다.")
    @Test
    void update_titleTooShort() throws Exception {
        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "공",
                                    "content": "수정된 내용",
                                    "status": "HIDDEN"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("수정 시 title이 100자 초과이면 400을 반환한다.")
    @Test
    void update_titleTooLong() throws Exception {
        String longTitle = "공".repeat(101);

        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "%s",
                                    "content": "수정된 내용",
                                    "status": "HIDDEN"
                                }
                                """.formatted(longTitle)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("수정 시 content가 없으면 400을 반환한다.")
    @Test
    void update_contentIsBlank() throws Exception {
        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "수정된 제목",
                                    "status": "HIDDEN"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("수정 시 status가 없으면 400을 반환한다.")
    @Test
    void update_statusIsNull() throws Exception {
        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "수정된 제목",
                                    "content": "수정된 내용"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("정상 요청 시 공지사항이 삭제되고 200을 반환한다.")
    @Test
    void deleteAnnouncement() throws Exception {
        given(deleteAnnouncementUseCase.deleteAnnouncement(anyLong())).willReturn(1L);

        mockMvc.perform(delete("/api/announcements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}