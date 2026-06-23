package com.ohgiraffers.lxp.announcement;

import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementJpaEntity;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementRepository;
import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AnnouncementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @AfterEach
    void cleanup() {
        announcementRepository.deleteAll();
    }

    @DisplayName("공지사항 등록")
    @Test
    void createAnnouncement() throws Exception {
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
                .andExpect(jsonPath("$.id").isNumber());

        assertThat(announcementRepository.count()).isEqualTo(1);
    }

    @DisplayName("공지사항 수정")
    @Test
    void updateAnnouncement() throws Exception {
        AnnouncementJpaEntity saved = announcementRepository.save(
                new AnnouncementJpaEntity(1L, "원래 제목", "원래 내용", AnnouncementStatus.PUBLISH)
        );

        mockMvc.perform(put("/api/announcements/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "수정된 제목",
                                    "content": "수정된 내용",
                                    "status": "HIDDEN"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.status").value("HIDDEN"));

        AnnouncementJpaEntity updated = announcementRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getTitle()).isEqualTo("수정된 제목");
        assertThat(updated.getStatus()).isEqualTo(AnnouncementStatus.HIDDEN);
    }

    @DisplayName("공지사항 삭제")
    @Test
    void deleteAnnouncement() throws Exception {
        AnnouncementJpaEntity saved = announcementRepository.save(
                new AnnouncementJpaEntity(1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH)
        );

        mockMvc.perform(delete("/api/announcements/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()));

        assertThat(announcementRepository.findById(saved.getId())).isEmpty();
    }
}
