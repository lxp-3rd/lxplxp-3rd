package com.ohgiraffers.lxp.roadmap.application.dto;

import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;

import java.time.LocalDateTime;
import java.util.List;

public record RoadmapResult(
        Long id,
        Long memberId,
        String name,
        String introduction,
        List<Long> courseIds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static RoadmapResult from(Roadmap roadmap) {
        return new RoadmapResult(
                roadmap.getId(),
                roadmap.getMemberId(),
                roadmap.getName(),
                roadmap.getIntroduction(),
                roadmap.getCourseIds(),
                roadmap.getCreatedAt(),
                roadmap.getUpdatedAt()
        );
    }
}
