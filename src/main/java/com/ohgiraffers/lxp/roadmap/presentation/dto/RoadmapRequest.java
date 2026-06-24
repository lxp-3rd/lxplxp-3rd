package com.ohgiraffers.lxp.roadmap.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ohgiraffers.lxp.roadmap.application.port.command.CreateRoadmapCommand;
import com.ohgiraffers.lxp.roadmap.application.port.command.UpdateRoadmapCommand;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RoadmapRequest(
        @NotBlank
        @Size(min = 2, max = 20)
        String name,

        @NotBlank
        @Size(min = 20, max = 200)
        String introduction,

        @NotNull
        @Size(min = 2)
        List<@NotNull Long> courseIds
) {

    public CreateRoadmapCommand toCreateCommand(Long memberId) {
        return new CreateRoadmapCommand(memberId, name, introduction, courseIds);
    }

    public UpdateRoadmapCommand toUpdateCommand(Long roadmapId, Long memberId) {
        return new UpdateRoadmapCommand(roadmapId, memberId, name, introduction, courseIds);
    }

    @AssertTrue
    @JsonIgnore
    public boolean isCourseIdsDistinct() {
        return courseIds == null || courseIds.stream().distinct().count() == courseIds.size();
    }
}
