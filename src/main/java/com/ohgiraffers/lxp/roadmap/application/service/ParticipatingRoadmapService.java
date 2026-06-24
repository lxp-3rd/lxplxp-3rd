package com.ohgiraffers.lxp.roadmap.application.service;

import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.in.ParticipatingRoadmapUseCase;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapParticipationPort;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapRepositoryPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ParticipatingRoadmapService implements ParticipatingRoadmapUseCase {

    private final RoadmapParticipationPort roadmapParticipationPort;
    private final RoadmapRepositoryPort roadmapRepositoryPort;

    public ParticipatingRoadmapService(
            RoadmapParticipationPort roadmapParticipationPort,
            RoadmapRepositoryPort roadmapRepositoryPort
    ) {
        this.roadmapParticipationPort = roadmapParticipationPort;
        this.roadmapRepositoryPort = roadmapRepositoryPort;
    }

    @Override
    public List<RoadmapResult> getParticipatingRoadmaps(Long memberId) {
        List<ParticipatingRoadmap> participatingRoadmaps = roadmapParticipationPort.findAllByMemberId(memberId);
        return roadmapRepositoryPort.findAllByParticipatingRoadmaps(participatingRoadmaps)
                .stream()
                .map(RoadmapResult::from)
                .toList();
    }
}
