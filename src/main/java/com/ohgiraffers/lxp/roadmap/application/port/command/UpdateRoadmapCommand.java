package com.ohgiraffers.lxp.roadmap.application.port.command;

import java.util.List;

public record UpdateRoadmapCommand(
        Long roadmapId,
        Long memberId,
        String name,
        String introduction,
        List<Long> courseIds
) {
}
