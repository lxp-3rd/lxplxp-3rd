package com.ohgiraffers.lxp.roadmap.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;
import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Import({ParticipatingRoadmapPersistenceAdapter.class, ParticipatingRoadmapPersistenceAdapterTest.AuditingConfig.class})
@DisplayName("참여 중인 로드맵 영속성 어댑터 테스트")
class ParticipatingRoadmapPersistenceAdapterTest {

    @TestConfiguration
    @EnableJpaAuditing
    static class AuditingConfig {
    }

    @Autowired
    private ParticipatingRoadmapPersistenceAdapter adapter;

    @Autowired
    private ParticipatingRoadmapJpaRepository repository;

    @Test
    @DisplayName("findAllByMemberId: ACTIVE이고 삭제되지 않은 참여 로드맵만 조회한다")
    void findAllByMemberId() {
        ParticipatingRoadmapJpaEntity active = repository.save(new ParticipatingRoadmapJpaEntity(
                1L,
                10L,
                ParticipatingRoadmapStatus.ACTIVE
        ));
        repository.save(new ParticipatingRoadmapJpaEntity(
                1L,
                20L,
                ParticipatingRoadmapStatus.CANCELED
        ));
        repository.save(new ParticipatingRoadmapJpaEntity(
                2L,
                30L,
                ParticipatingRoadmapStatus.ACTIVE
        ));
        ParticipatingRoadmapJpaEntity deleted = repository.save(new ParticipatingRoadmapJpaEntity(
                1L,
                40L,
                ParticipatingRoadmapStatus.ACTIVE
        ));
        deleted.delete();

        List<ParticipatingRoadmap> results = adapter.findAllByMemberId(1L);

        assertThat(results)
                .extracting(ParticipatingRoadmap::getId)
                .containsExactly(active.getId());
        assertThat(results.get(0).getRoadmapId()).isEqualTo(10L);
    }
}
