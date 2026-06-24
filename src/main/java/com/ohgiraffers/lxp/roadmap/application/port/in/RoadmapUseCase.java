package com.ohgiraffers.lxp.roadmap.application.port.in;

import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.command.CreateRoadmapCommand;
import com.ohgiraffers.lxp.roadmap.application.port.command.UpdateRoadmapCommand;

import java.util.List;

public interface RoadmapUseCase {

    RoadmapResult createRoadmap(CreateRoadmapCommand command);

    List<RoadmapResult> getAllRoadmaps();

    RoadmapResult getRoadmap(Long roadmapId);

    RoadmapResult getRoadmap(Long roadmapId, Long memberId);

    List<RoadmapResult> getAvailableRoadmaps(Long memberId);

    List<RoadmapResult> getCreatedRoadmaps(Long memberId);

    RoadmapResult updateRoadmap(UpdateRoadmapCommand command);

    void deleteRoadmap(Long roadmapId, Long memberId);
}
