package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.config.JpaAuditingConfig;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class InstructorProfileJpaEntityTest {

    @Autowired
    private InstructorProfileJpaRepository jpaRepository;

    @Test
    @DisplayName("프로필 저장 시 ID와 createdAt이 자동 설정된다")
    void save_idAndCreatedAtAreGenerated() {
        InstructorProfile domain = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );

        InstructorProfileJpaEntity saved = jpaRepository.save(InstructorProfileJpaEntity.from(domain));

        assertThat(saved.toDomain().getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("강사 ID로 프로필을 조회할 수 있다")
    void findByInstructorId_returnsProfile() {
        InstructorProfile domain = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );
        jpaRepository.save(InstructorProfileJpaEntity.from(domain));

        Optional<InstructorProfileJpaEntity> found = jpaRepository.findByInstructorId(1L);

        assertThat(found).isPresent();
        assertThat(found.get().toDomain().getInstructorId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 강사 ID로 조회 시 빈 값을 반환한다")
    void findByInstructorId_returnsEmptyWhenNotExists() {
        Optional<InstructorProfileJpaEntity> found = jpaRepository.findByInstructorId(99L);

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("프로필 수정 후 저장 시 변경된 값이 반영된다")
    void save_afterUpdate_reflectsChanges() {
        InstructorProfile domain = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "기존 자기소개"
        );
        InstructorProfileJpaEntity saved = jpaRepository.saveAndFlush(InstructorProfileJpaEntity.from(domain));

        InstructorProfile updated = saved.toDomain();
        updated.update("https://example.com/new.jpg", "새로운 자기소개");
        jpaRepository.saveAndFlush(InstructorProfileJpaEntity.from(updated));

        InstructorProfileJpaEntity found = jpaRepository.findByInstructorId(1L).get();
        assertThat(found.toDomain().getProfileImageUrl()).isEqualTo("https://example.com/new.jpg");
        assertThat(found.toDomain().getBio()).isEqualTo("새로운 자기소개");
    }
}
