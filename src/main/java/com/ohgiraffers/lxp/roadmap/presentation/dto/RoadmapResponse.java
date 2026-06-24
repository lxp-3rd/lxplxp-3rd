package com.ohgiraffers.lxp.roadmap.presentation.dto;

import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;

import java.time.LocalDateTime;
import java.util.List;

public record RoadmapResponse(
        Long id,
        Long memberId,
        String name,
        String introduction,
        List<Long> courseIds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static RoadmapResponse from(RoadmapResult result) {
        return new RoadmapResponse(
                result.id(),
                result.memberId(),
                result.name(),
                result.introduction(),
                result.courseIds(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
