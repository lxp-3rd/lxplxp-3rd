package com.ohgiraffers.lxp.enrollment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestPropertySource(properties = "spring.sql.init.mode=never")
@Transactional
@DisplayName("수강 재신청 통합 테스트")
class EnrollmentReEnrollIntegrationTest {

    private static final long MEMBER_ID = 1L;
    private static final long COURSE_ID = 2L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnrollmentJpaRepository repository;

    @BeforeEach
    void clearEnrollments() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("이전 수강이 CANCELED면 재신청 시 새 ACTIVE row가 생성된다")
    void reEnrollAfterCanceledCreatesNewRow() throws Exception {
        // given: 동일 회원·강좌의 CANCELED 수강이 이미 존재(취소된 상태를 직접 시드)
        repository.save(new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.CANCELED));

        // when: 재신청
        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new EnrollmentRequest(MEMBER_ID, COURSE_ID))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        // then: 기존 CANCELED + 신규 ACTIVE = 2건 (reactivate 아님, 새 row)
        List<EnrollmentJpaEntity> all = repository.findAll();
        assertThat(all)
                .hasSize(2)
                .extracting(EnrollmentJpaEntity::getMemberId,
                        EnrollmentJpaEntity::getCourseId,
                        EnrollmentJpaEntity::getStatus)
                .containsExactlyInAnyOrder(
                        tuple(MEMBER_ID, COURSE_ID, EnrollmentStatus.CANCELED),
                        tuple(MEMBER_ID, COURSE_ID, EnrollmentStatus.ACTIVE));
    }

    @Test
    @DisplayName("ACTIVE 수강이 있으면 재신청은 409로 차단되고 row가 늘지 않는다")
    void reEnrollWhileActiveIsBlocked() throws Exception {
        // given: 동일 회원·강좌의 ACTIVE 수강이 이미 존재
        repository.save(new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.ACTIVE));

        // when: 재신청 → 중복(ENROLLMENT_ALREADY_EXISTS) 차단
        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new EnrollmentRequest(MEMBER_ID, COURSE_ID))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ENROLLMENT_ALREADY_EXISTS"));

        // then: row 그대로 1건
        assertThat(repository.findAll()).hasSize(1);
    }
}
