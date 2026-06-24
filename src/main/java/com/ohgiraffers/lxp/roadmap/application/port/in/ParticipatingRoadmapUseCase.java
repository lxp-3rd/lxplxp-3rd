package com.ohgiraffers.lxp.roadmap.application.port.in;

import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;

import java.util.List;

public interface ParticipatingRoadmapUseCase {

    List<RoadmapResult> getParticipatingRoadmaps(Long memberId);

    void participate(Long roadmapId, Long memberId);
}
