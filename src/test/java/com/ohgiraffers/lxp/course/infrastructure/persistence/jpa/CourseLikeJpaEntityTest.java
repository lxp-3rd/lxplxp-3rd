package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import com.ohgiraffers.lxp.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class CourseLikeJpaEntityTest {

    @Autowired
    private CourseLikeJpaRepository courseLikeJpaRepository;

    @Test
    @DisplayName("좋아요를 저장하면 createdAt이 자동 설정된다")
    void save_success_createdAtIsSet() {
        CourseLike like = CourseLike.create(1L, 100L);
        CourseLikeJpaEntity entity = CourseLikeJpaEntity.from(like);

        CourseLikeJpaEntity saved = courseLikeJpaRepository.save(entity);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.toDomain().getCreatedAt()).isNotNull();
        assertThat(saved.toDomain().getCourseId()).isEqualTo(1L);
        assertThat(saved.toDomain().getLearnerId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("같은 수강생이 같은 강좌에 좋아요를 두 번 누르면 UNIQUE 제약 위반 예외가 발생한다")
    void save_duplicateLike_throwsUniqueViolation() {
        CourseLikeJpaEntity first = CourseLikeJpaEntity.from(CourseLike.create(1L, 100L));
        courseLikeJpaRepository.save(first);

        CourseLikeJpaEntity second = CourseLikeJpaEntity.from(CourseLike.create(1L, 100L));

        assertThatThrownBy(() -> courseLikeJpaRepository.saveAndFlush(second))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("courseId와 learnerId로 좋아요를 조회할 수 있다")
    void findByCourseIdAndLearnerId_success() {
        CourseLikeJpaEntity entity = CourseLikeJpaEntity.from(CourseLike.create(1L, 100L));
        courseLikeJpaRepository.save(entity);

        Optional<CourseLikeJpaEntity> found = courseLikeJpaRepository.findByCourseIdAndLearnerId(1L, 100L);

        assertThat(found).isPresent();
        assertThat(found.get().toDomain().getLearnerId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("강좌 좋아요 수를 courseId로 집계할 수 있다")
    void countByCourseId_success() {
        courseLikeJpaRepository.save(CourseLikeJpaEntity.from(CourseLike.create(1L, 100L)));
        courseLikeJpaRepository.save(CourseLikeJpaEntity.from(CourseLike.create(1L, 200L)));

        long count = courseLikeJpaRepository.countByCourseId(1L);

        assertThat(count).isEqualTo(2L);
    }
}
