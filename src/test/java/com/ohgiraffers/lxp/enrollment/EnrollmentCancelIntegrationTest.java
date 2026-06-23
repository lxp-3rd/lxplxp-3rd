package com.ohgiraffers.lxp.enrollment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Transactional
@DisplayName("수강취소 통합 테스트")
class EnrollmentCancelIntegrationTest {

    private static final long MEMBER_ID = 1L;
    private static final long COURSE_ID = 2L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentJpaRepository repository;

    @Test
    @DisplayName("ACTIVE 수강을 DELETE → 200 + CANCELED, row는 CANCELED·deletedAt 기록(soft delete)")
    void cancelActiveSoftDeletes() throws Exception {
        EnrollmentJpaEntity seeded = repository.save(
                new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.ACTIVE));

        mockMvc.perform(delete("/enrollments/" + seeded.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(seeded.getId()))
                .andExpect(jsonPath("$.status").value("CANCELED"));

        EnrollmentJpaEntity canceled = repository.findById(seeded.getId()).orElseThrow();
        assertThat(canceled.getStatus()).isEqualTo(EnrollmentStatus.CANCELED);
        assertThat(canceled.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("이미 CANCELED인 수강을 다시 DELETE → 400 ENROLLMENT_ALREADY_CANCELED")
    void reCancelIsBlocked() throws Exception {
        EnrollmentJpaEntity seeded = repository.save(
                new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.CANCELED));

        mockMvc.perform(delete("/enrollments/" + seeded.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ENROLLMENT_ALREADY_CANCELED"));
    }

    @Test
    @DisplayName("존재하지 않는 수강을 DELETE → 404 ENROLLMENT_NOT_FOUND")
    void cancelNonExistent() throws Exception {
        mockMvc.perform(delete("/enrollments/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ENROLLMENT_NOT_FOUND"));
    }
}
