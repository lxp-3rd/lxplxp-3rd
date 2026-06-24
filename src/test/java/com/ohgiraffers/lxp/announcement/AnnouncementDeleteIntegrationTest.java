package com.ohgiraffers.lxp.announcement;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementJpaEntity;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementRepository;
import com.ohgiraffers.lxp.auth.infrastructure.token.JwtTokenIssueAdapter;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AnnouncementDeleteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private JwtTokenIssueAdapter jwtTokenIssueAdapter;

    @AfterEach
    void cleanup() {
        announcementRepository.deleteAll();
    }

    @DisplayName("공지사항 삭제 시 200을 반환하고 DB에서 제거된다.")
    @Test
    void deleteAnnouncement() throws Exception {
        AnnouncementJpaEntity saved = announcementRepository.save(
                new AnnouncementJpaEntity(1L, "6월 22일 공지사항", "6월 22일 공지사항입니다.", AnnouncementStatus.PUBLISH)
        );

        mockMvc.perform(delete("/api/announcements/" + saved.getId())
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()));

        assertThat(announcementRepository.findById(saved.getId())).isEmpty();
    }

    @DisplayName("존재하지 않는 공지사항 삭제 시 404를 반환한다.")
    @Test
    void deleteAnnouncement_notFound() throws Exception {
        mockMvc.perform(delete("/api/announcements/999")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken()))
                .andExpect(status().isNotFound());
    }

    private String bearerAdminToken() {
        return "Bearer " + jwtTokenIssueAdapter.issueAccessToken(1L, MemberRole.ADMIN.name());
    }
}