package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Import({RoadmapPersistenceAdapter.class, RoadmapPersistenceAdapterTest.AuditingConfig.class})
@DisplayName("로드맵 영속성 어댑터 테스트")
class RoadmapPersistenceAdapterTest {

    @TestConfiguration
    @EnableJpaAuditing
    static class AuditingConfig {
    }

    @Autowired
    private RoadmapPersistenceAdapter adapter;

    @Autowired
    private RoadmapJpaRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("save: 로드맵과 강좌 목록을 저장한다")
    void save() {
        Roadmap roadmap = Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        );

        Roadmap saved = adapter.save(roadmap);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMemberId()).isEqualTo(1L);
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getCourseIds()).containsExactly(1L, 2L);
        assertThat(repository.findById(saved.getId())).isPresent();
    }

    @Test
    @DisplayName("findById: 저장된 로드맵을 도메인으로 복원한다")
    void findById() {
        Roadmap saved = adapter.save(Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        ));

        Roadmap found = adapter.findById(saved.getId()).orElseThrow();

        assertThat(found.getName()).isEqualTo("백엔드 로드맵");
        assertThat(found.getMemberId()).isEqualTo(1L);
        assertThat(found.getCourseIds()).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("findAll: 삭제되지 않은 로드맵만 조회한다")
    void findAll() {
        Roadmap active = adapter.save(Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        ));
        Roadmap deleted = adapter.save(Roadmap.create(
                1L,
                "프론트 로드맵",
                "프론트엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(2L, 3L)
        ));
        adapter.deleteById(deleted.getId());

        adapter.save(Roadmap.create(
                2L,
                "다른 로드맵",
                "다른 회원이 만든 강좌 로드맵 소개 문장입니다.",
                List.of(1L, 3L)
        ));

        List<Roadmap> roadmaps = adapter.findAllByMemberId(1L);

        assertThat(roadmaps)
                .extracting(Roadmap::getId)
                .containsExactly(active.getId());
    }

    @Test
    @DisplayName("save: 기존 로드맵을 수정하면 강좌 목록이 교체된다")
    void update() {
        Roadmap saved = adapter.save(Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        ));

        Roadmap updated = adapter.save(saved.update(
                "수정 로드맵",
                "수정된 강좌 로드맵 소개 문장을 충분히 작성합니다.",
                List.of(2L, 3L)
        ));

        assertThat(updated.getName()).isEqualTo("수정 로드맵");
        assertThat(updated.getCourseIds()).containsExactly(2L, 3L);
    }

    @Test
    @DisplayName("deleteById: 로드맵을 soft delete 처리한다")
    void deleteById() {
        Roadmap saved = adapter.save(Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        ));

        adapter.deleteById(saved.getId());

        em.flush();
        em.clear();
        RoadmapJpaEntity entity = repository.findById(saved.getId()).orElseThrow();
        assertThat(entity.getDeletedAt()).isNotNull();
        assertThat(adapter.findById(saved.getId())).isEmpty();
    }
}
