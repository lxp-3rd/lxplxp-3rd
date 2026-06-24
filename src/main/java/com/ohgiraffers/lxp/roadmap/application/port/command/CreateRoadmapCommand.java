package com.ohgiraffers.lxp.roadmap.application.port.command;

import java.util.List;

public record CreateRoadmapCommand(
        Long memberId,
        String name,
        String introduction,
        List<Long> courseIds
) {
}
