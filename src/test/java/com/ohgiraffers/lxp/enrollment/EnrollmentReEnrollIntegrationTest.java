package com.ohgiraffers.lxp.enrollment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaEntity;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaRepository;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentRequest;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaEntity;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaRepository;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnrollmentJpaRepository repository;

    @Autowired
    private MemberJpaRepository memberRepository;

    @Autowired
    private CourseJpaRepository courseRepository;

    private long memberId;
    private long courseId;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        MemberJpaEntity savedMember = memberRepository.save(MemberJpaEntity.from(
                Member.signUp(new Email("learner@test.com"), new Nickname("learner"), new EncodedPassword("pwd"))
        ));
        memberId = savedMember.getId();

        CourseJpaEntity hiddenCourse = courseRepository.save(CourseJpaEntity.from(
                Course.create(999L, "테스트 강좌", "테스트 강좌 설명", null)
        ));
        Course courseDomain = hiddenCourse.toDomain();
        courseDomain.changeStatus(com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus.PUBLIC, null);
        CourseJpaEntity savedCourse = courseRepository.save(CourseJpaEntity.from(courseDomain));
        courseId = savedCourse.getId();
    }

    @Test
    @DisplayName("이전 수강이 CANCELED면 재신청 시 새 ACTIVE row가 생성된다")
    void reEnrollAfterCanceledCreatesNewRow() throws Exception {
        repository.save(new EnrollmentJpaEntity(memberId, courseId, EnrollmentStatus.CANCELED));

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new EnrollmentRequest(memberId, courseId))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        List<EnrollmentJpaEntity> all = repository.findAll();
        assertThat(all)
                .hasSize(2)
                .extracting(EnrollmentJpaEntity::getMemberId,
                        EnrollmentJpaEntity::getCourseId,
                        EnrollmentJpaEntity::getStatus)
                .containsExactlyInAnyOrder(
                        tuple(memberId, courseId, EnrollmentStatus.CANCELED),
                        tuple(memberId, courseId, EnrollmentStatus.ACTIVE));
    }

    @Test
    @DisplayName("ACTIVE 수강이 있으면 재신청은 409로 차단되고 row가 늘지 않는다")
    void reEnrollWhileActiveIsBlocked() throws Exception {
        repository.save(new EnrollmentJpaEntity(memberId, courseId, EnrollmentStatus.ACTIVE));

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new EnrollmentRequest(memberId, courseId))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ENROLLMENT_ALREADY_EXISTS"));

        assertThat(repository.findAll()).hasSize(1);
    }
}
