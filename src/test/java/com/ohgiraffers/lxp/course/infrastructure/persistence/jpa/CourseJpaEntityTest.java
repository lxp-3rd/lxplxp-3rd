package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class CourseJpaEntityTest {

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("강좌를 저장하고 조회할 수 있다")
    void saveAndFind_success() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", null);
        CourseJpaEntity entity = CourseJpaEntity.from(course);

        CourseJpaEntity saved = courseJpaRepository.save(entity);

        Optional<CourseJpaEntity> found = courseJpaRepository.findByIdAndDeletedAtIsNull(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().toDomain().getTitle()).isEqualTo("강좌 제목");
        assertThat(found.get().toDomain().getStatus()).isEqualTo(CourseStatus.HIDDEN);
    }

    @Test
    @DisplayName("soft delete 이후 findByIdAndDeletedAtIsNull 조회에서 제외된다")
    void softDelete_notFoundAfterDelete() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", null);
        CourseJpaEntity entity = CourseJpaEntity.from(course);
        CourseJpaEntity saved = courseJpaRepository.save(entity);

        saved.delete();
        courseJpaRepository.save(saved);

        Optional<CourseJpaEntity> found = courseJpaRepository.findByIdAndDeletedAtIsNull(saved.getId());
        assertThat(found).isEmpty();
    }
}
