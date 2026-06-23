package com.ohgiraffers.lxp.roadmap.domain.model.entity;

import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("참여 중인 로드맵 도메인 테스트")
class ParticipatingRoadmapTest {

    @Test
    @DisplayName("회원 ID와 로드맵 ID로 참여 중인 로드맵을 생성한다")
    void participate_success() {
        ParticipatingRoadmap participatingRoadmap = ParticipatingRoadmap.participate(1L, 2L);

        assertThat(participatingRoadmap.getId()).isNull();
        assertThat(participatingRoadmap.getMemberId()).isEqualTo(1L);
        assertThat(participatingRoadmap.getRoadmapId()).isEqualTo(2L);
        assertThat(participatingRoadmap.getStatus()).isEqualTo(ParticipatingRoadmapStatus.ACTIVE);
        assertThat(participatingRoadmap.isParticipatedBy(1L)).isTrue();
    }

    @Test
    @DisplayName("저장된 참여 중인 로드맵을 복원한다")
    void restore_success() {
        ParticipatingRoadmap participatingRoadmap = ParticipatingRoadmap.restore(
                10L,
                1L,
                2L,
                ParticipatingRoadmapStatus.ACTIVE
        );

        assertThat(participatingRoadmap.getId()).isEqualTo(10L);
        assertThat(participatingRoadmap.getMemberId()).isEqualTo(1L);
        assertThat(participatingRoadmap.getRoadmapId()).isEqualTo(2L);
        assertThat(participatingRoadmap.getStatus()).isEqualTo(ParticipatingRoadmapStatus.ACTIVE);
    }

    @Test
    @DisplayName("회원 ID는 필수다")
    void participate_memberIdRequired() {
        assertThatThrownBy(() -> ParticipatingRoadmap.participate(null, 2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("memberId must not be null");
    }

    @Test
    @DisplayName("로드맵 ID는 필수다")
    void participate_roadmapIdRequired() {
        assertThatThrownBy(() -> ParticipatingRoadmap.participate(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("roadmapId must not be null");
    }

    @Test
    @DisplayName("상태는 필수다")
    void restore_statusRequired() {
        assertThatThrownBy(() -> ParticipatingRoadmap.restore(1L, 1L, 2L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("status must not be null");
    }
}
