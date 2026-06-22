package com.ohgiraffers.lxp.enrollment.infrastructure;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.EnrollmentPersistenceAdapter;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.test.context.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Import({EnrollmentPersistenceAdapter.class, EnrollmentPersistenceAdapterTest.AuditingConfig.class})
@DisplayName("수강 영속성 어댑터 테스트")
class EnrollmentPersistenceAdapterTest {

    private static final long MEMBER_ID = 1L;
    private static final long COURSE_ID = 2L;

    @TestConfiguration
    @EnableJpaAuditing
    static class AuditingConfig {
        // @DataJpaTest는 메인 클래스의 @EnableJpaAuditing을 적용하지 않으므로 여기서 감사를 켠다.
    }

    @Autowired
    private EnrollmentPersistenceAdapter adapter;

    @Autowired
    private EnrollmentJpaRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("save: 저장하면 id와 신청일시(createdAt)가 채워진 ACTIVE 결과를 반환한다")
    void saveReturnsResultWithAudit() {
        EnrollmentResult result = adapter.save(Enrollment.enroll(MEMBER_ID, COURSE_ID));

        assertThat(result.id()).isNotNull();
        assertThat(result.memberId()).isEqualTo(MEMBER_ID);
        assertThat(result.courseId()).isEqualTo(COURSE_ID);
        assertThat(result.status()).isEqualTo(EnrollmentStatus.ACTIVE);
        assertThat(result.createdAt()).isNotNull();
        assertThat(repository.findById(result.id())).isPresent();
    }

    @Test
    @DisplayName("existsActiveEnrollment: ACTIVE 수강이 있으면 true")
    void existsActiveTrue() {
        em.persist(new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.ACTIVE));

        assertThat(adapter.existsActiveEnrollment(MEMBER_ID, COURSE_ID)).isTrue();
    }

    @Test
    @DisplayName("existsActiveEnrollment: CANCELED만 있으면 false (재신청 허용)")
    void existsActiveFalseWhenOnlyCanceled() {
        em.persist(new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.CANCELED));

        assertThat(adapter.existsActiveEnrollment(MEMBER_ID, COURSE_ID)).isFalse();
    }

    @Test
    @DisplayName("existsActiveEnrollment: 다른 회원/강좌면 false")
    void existsActiveFalseForOther() {
        em.persist(new EnrollmentJpaEntity(MEMBER_ID, COURSE_ID, EnrollmentStatus.ACTIVE));

        assertThat(adapter.existsActiveEnrollment(99L, COURSE_ID)).isFalse();
        assertThat(adapter.existsActiveEnrollment(MEMBER_ID, 99L)).isFalse();
    }
}
