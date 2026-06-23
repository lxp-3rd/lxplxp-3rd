package com.ohgiraffers.lxp.announcement.presentation.web;

import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnouncementController.class)
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateAnnouncementUseCase createAnnouncementUseCase;

    @DisplayName("정상 요청 시 공지사항이 등록되고 201을 반환한다.")
    @Test
    void create() throws Exception {
        given(createAnnouncementUseCase.createAnnouncement(any())).willReturn(1L);

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
                .andExpect(jsonPath("$.id").value(1L));
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
}