package com.ohgiraffers.lxp.roadmap.application.port.out;

import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;

import java.util.List;
import java.util.Optional;

public interface RoadmapRepositoryPort {

    Roadmap save(Roadmap roadmap);

    Optional<Roadmap> findById(Long roadmapId);

    List<Roadmap> findAllByMemberId(Long memberId);

    void deleteById(Long roadmapId);
}
