package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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

        assertThat(saved.getId()).isNotNull();
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
}
