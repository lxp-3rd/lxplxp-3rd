package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;
import com.ohgiraffers.lxp.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class InstructorApplicationJpaEntityTest {

    @Autowired
    private InstructorApplicationJpaRepository jpaRepository;

    @Test
    @DisplayName("강사 신청 저장 시 ID와 createdAt이 자동 설정된다")
    void save_idAndCreatedAtAreGenerated() {
        InstructorApplication domain = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        InstructorApplicationJpaEntity entity = InstructorApplicationJpaEntity.from(domain);

        InstructorApplicationJpaEntity saved = jpaRepository.save(entity);

        assertThat(saved.toDomain().getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("저장된 강사 신청의 상태는 PENDING이다")
    void save_statusIsPending() {
        InstructorApplication domain = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        InstructorApplicationJpaEntity entity = InstructorApplicationJpaEntity.from(domain);

        InstructorApplicationJpaEntity saved = jpaRepository.save(entity);

        assertThat(saved.toDomain().getStatus()).isEqualTo(ApplicationStatus.PENDING);
    }

    @Test
    @DisplayName("동일 회원의 PENDING 신청 존재 여부를 조회할 수 있다")
    void existsByMemberIdAndStatus_returnsTrueWhenExists() {
        InstructorApplication domain = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        jpaRepository.save(InstructorApplicationJpaEntity.from(domain));

        boolean exists = jpaRepository.existsByMemberIdAndStatus(1L, ApplicationStatus.PENDING);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("신청이 없는 회원의 PENDING 조회 시 false를 반환한다")
    void existsByMemberIdAndStatus_returnsFalseWhenNotExists() {
        boolean exists = jpaRepository.existsByMemberIdAndStatus(99L, ApplicationStatus.PENDING);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("동일 회원의 PENDING 신청이 이미 있으면 DB 유니크 제약 위반 예외가 발생한다")
    void save_duplicatePending_throwsDataIntegrityViolationException() {
        InstructorApplication first = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        jpaRepository.saveAndFlush(InstructorApplicationJpaEntity.from(first));

        InstructorApplication second = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        assertThatThrownBy(() ->
                jpaRepository.saveAndFlush(InstructorApplicationJpaEntity.from(second)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("REJECTED 처리 후 동일 회원의 PENDING 재신청이 가능하다")
    void save_pendingAfterRejected_succeeds() {
        // 1. 최초 PENDING 저장
        InstructorApplication first = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        InstructorApplicationJpaEntity savedFirst =
                jpaRepository.saveAndFlush(InstructorApplicationJpaEntity.from(first));

        // 2. 반려 처리 (PENDING → REJECTED, pending_lock → null)
        InstructorApplication firstDomain = savedFirst.toDomain();
        firstDomain.reject("기준 미달", LocalDateTime.now());
        jpaRepository.saveAndFlush(InstructorApplicationJpaEntity.from(firstDomain));

        // 3. 동일 회원 재신청 (새 PENDING 레코드)
        InstructorApplication second = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        InstructorApplicationJpaEntity savedSecond =
                jpaRepository.saveAndFlush(InstructorApplicationJpaEntity.from(second));

        assertThat(savedSecond.toDomain().getId()).isNotNull();
        assertThat(savedSecond.toDomain().getStatus()).isEqualTo(ApplicationStatus.PENDING);
    }
}
