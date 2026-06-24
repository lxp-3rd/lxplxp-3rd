package com.ohgiraffers.lxp.roadmap.application.service;

import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapParticipationPort;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapRepositoryPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;
import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("참여 중인 로드맵 서비스 테스트")
class ParticipatingRoadmapServiceTest {

    @InjectMocks
    private ParticipatingRoadmapService participatingRoadmapService;

    @Mock
    private RoadmapParticipationPort roadmapParticipationPort;

    @Mock
    private RoadmapRepositoryPort roadmapRepositoryPort;

    @Test
    @DisplayName("참여 중인 로드맵은 참여 도메인 모델 기준으로 조회한다")
    void getParticipatingRoadmaps_success() {
        Roadmap roadmap = Roadmap.restore(
                2L, 3L, "참여 로드맵", "참여 중인 강좌 로드맵 소개 문장입니다.", List.of(1L, 2L), null, null
        );
        List<ParticipatingRoadmap> participatingRoadmaps = List.of(
                ParticipatingRoadmap.restore(1L, 1L, 2L, ParticipatingRoadmapStatus.ACTIVE)
        );
        given(roadmapParticipationPort.findAllByMemberId(1L)).willReturn(participatingRoadmaps);
        given(roadmapRepositoryPort.findAllByParticipatingRoadmaps(participatingRoadmaps)).willReturn(List.of(roadmap));

        List<RoadmapResult> results = participatingRoadmapService.getParticipatingRoadmaps(1L);

        assertThat(results).extracting(RoadmapResult::id).containsExactly(2L);
    }
}
