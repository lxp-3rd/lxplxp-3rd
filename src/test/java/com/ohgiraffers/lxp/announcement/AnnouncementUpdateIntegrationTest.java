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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestPropertySource(properties = "spring.sql.init.mode=never")
class AnnouncementUpdateIntegrationTest {

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

    @DisplayName("공지사항 수정 시 200을 반환하고 DB에 반영된다.")
    @Test
    void updateAnnouncement() throws Exception {
        AnnouncementJpaEntity saved = announcementRepository.save(
                new AnnouncementJpaEntity(1L, "원래 제목", "원래 내용", AnnouncementStatus.PUBLISH)
        );

        mockMvc.perform(put("/api/announcements/" + saved.getId())
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
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
        assertThat(updated.getContent()).isEqualTo("수정된 내용");
        assertThat(updated.getStatus()).isEqualTo(AnnouncementStatus.HIDDEN);
    }

    @DisplayName("존재하지 않는 공지사항 수정 시 404를 반환한다.")
    @Test
    void updateAnnouncement_notFound() throws Exception {
        mockMvc.perform(put("/api/announcements/999")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "수정된 제목",
                                    "content": "수정된 내용",
                                    "status": "HIDDEN"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    private String bearerAdminToken() {
        return "Bearer " + jwtTokenIssueAdapter.issueAccessToken(1L, MemberRole.ADMIN.name());
    }
}
