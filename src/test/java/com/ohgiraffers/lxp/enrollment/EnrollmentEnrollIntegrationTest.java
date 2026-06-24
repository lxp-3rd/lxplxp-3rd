package com.ohgiraffers.lxp.enrollment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
@DisplayName("수강신청 정상흐름 통합 테스트")
class EnrollmentEnrollIntegrationTest {

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
    @DisplayName("POST /enrollments → 201 + Location + 응답 본문, ACTIVE row 저장")
    void enrollHappyPath() throws Exception {
        EnrollmentRequest request = new EnrollmentRequest(memberId, courseId);

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.courseId").value(courseId))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.createdAt").exists());

        List<EnrollmentJpaEntity> all = repository.findAll();
        assertThat(all).hasSize(1);
        EnrollmentJpaEntity saved = all.get(0);
        assertThat(saved.getMemberId()).isEqualTo(memberId);
        assertThat(saved.getCourseId()).isEqualTo(courseId);
        assertThat(saved.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getDeletedAt()).isNull();
    }
}
