package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.config.JpaAuditingConfig;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class InstructorJpaEntityTest {

    @Autowired
    private InstructorJpaRepository jpaRepository;

    @Test
    @DisplayName("강사 저장 시 ID와 createdAt이 자동 설정된다")
    void save_idAndCreatedAtAreGenerated() {
        Instructor domain = Instructor.create(1L);

        InstructorJpaEntity saved = jpaRepository.save(InstructorJpaEntity.from(domain));

        assertThat(saved.toDomain().getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("저장된 강사의 상태는 ACTIVE이다")
    void save_statusIsActive() {
        Instructor domain = Instructor.create(1L);

        InstructorJpaEntity saved = jpaRepository.save(InstructorJpaEntity.from(domain));

        assertThat(saved.toDomain().getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    @DisplayName("ACTIVE 강사의 memberId로 조회 시 true를 반환한다")
    void existsByMemberIdAndStatusIn_returnsTrueForActive() {
        Instructor domain = Instructor.create(1L);
        jpaRepository.save(InstructorJpaEntity.from(domain));

        boolean exists = jpaRepository.existsByMemberIdAndStatusIn(
                1L, List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED));

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("강사가 없는 회원의 조회 시 false를 반환한다")
    void existsByMemberIdAndStatusIn_returnsFalseWhenNotExists() {
        boolean exists = jpaRepository.existsByMemberIdAndStatusIn(
                99L, List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED));

        assertThat(exists).isFalse();
    }
}
