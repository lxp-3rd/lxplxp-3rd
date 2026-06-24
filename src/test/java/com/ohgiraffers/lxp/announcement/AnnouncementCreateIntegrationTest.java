package com.ohgiraffers.lxp.announcement;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestPropertySource(properties = "spring.sql.init.mode=never")
class AnnouncementCreateIntegrationTest {

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

    @DisplayName("공지사항 등록 시 201을 반환하고 DB에 저장된다.")
    @Test
    void createAnnouncement() throws Exception {
        mockMvc.perform(post("/api/announcements")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
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

    private String bearerAdminToken() {
        return "Bearer " + jwtTokenIssueAdapter.issueAccessToken(1L, MemberRole.ADMIN.name());
    }
}
