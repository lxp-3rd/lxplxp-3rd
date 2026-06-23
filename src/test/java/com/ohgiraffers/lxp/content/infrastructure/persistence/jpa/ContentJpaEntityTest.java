package com.ohgiraffers.lxp.content.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class ContentJpaEntityTest {

    @Autowired
    private ContentJpaRepository contentJpaRepository;

    @Test
    @DisplayName("콘텐츠를 저장하면 id와 createdAt이 자동 설정된다")
    void save_success_idAndCreatedAtAreSet() {
        Content content = Content.create(1L, 0, "Java 기초", "https://example.com/video");
        ContentJpaEntity entity = ContentJpaEntity.from(content);

        ContentJpaEntity saved = contentJpaRepository.save(entity);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.toDomain().getCourseId()).isEqualTo(1L);
        assertThat(saved.toDomain().getSequence()).isEqualTo(0);
        assertThat(saved.toDomain().getTitle()).isEqualTo("Java 기초");
        assertThat(saved.toDomain().getContentUrl()).isEqualTo("https://example.com/video");
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("같은 강좌에 여러 콘텐츠를 저장할 수 있다")
    void save_multipleContents_success() {
        contentJpaRepository.save(ContentJpaEntity.from(Content.create(1L, 0, "1강", "https://example.com/1")));
        contentJpaRepository.save(ContentJpaEntity.from(Content.create(1L, 1, "2강", "https://example.com/2")));

        long count = contentJpaRepository.count();

        assertThat(count).isEqualTo(2);
    }
}
