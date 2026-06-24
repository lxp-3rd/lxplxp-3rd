package com.ohgiraffers.lxp.roadmap.application.port.out;

import com.ohgiraffers.lxp.roadmap.domain.model.entity.ParticipatingRoadmap;

import java.util.List;

public interface RoadmapParticipationPort {

    List<ParticipatingRoadmap> findAllByMemberId(Long memberId);

    boolean existsByMemberIdAndRoadmapId(Long memberId, Long roadmapId);

    ParticipatingRoadmap participate(Long memberId, Long roadmapId);
}
