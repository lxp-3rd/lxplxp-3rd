package com.ohgiraffers.lxp.roadmap.presentation.web;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMember;
import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.in.RoadmapUseCase;
import com.ohgiraffers.lxp.roadmap.presentation.dto.RoadmapRequest;
import com.ohgiraffers.lxp.roadmap.presentation.dto.RoadmapResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roadmaps")
public class RoadmapController {

    private final RoadmapUseCase roadmapUseCase;

    public RoadmapController(RoadmapUseCase roadmapUseCase) {
        this.roadmapUseCase = roadmapUseCase;
    }

    @PostMapping
    public ResponseEntity<RoadmapResponse> create(
            @LoginMember AuthenticatedMember authenticatedMember,
            @RequestBody @Valid RoadmapRequest request
    ) {
        RoadmapResult result = roadmapUseCase.createRoadmap(request.toCreateCommand(authenticatedMember.memberId()));
        return ResponseEntity
                .created(URI.create("/api/roadmaps/" + result.id()))
                .body(RoadmapResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoadmapResponse> get(
            @LoginMember AuthenticatedMember authenticatedMember,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(RoadmapResponse.from(roadmapUseCase.getRoadmap(id, authenticatedMember.memberId())));
    }

    @GetMapping
    public ResponseEntity<List<RoadmapResponse>> getAll(@LoginMember AuthenticatedMember authenticatedMember) {
        return ResponseEntity.ok(roadmapUseCase.getRoadmaps(authenticatedMember.memberId())
                .stream()
                .map(RoadmapResponse::from)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoadmapResponse> update(
            @LoginMember AuthenticatedMember authenticatedMember,
            @PathVariable Long id,
            @RequestBody @Valid RoadmapRequest request
    ) {
        return ResponseEntity.ok(RoadmapResponse.from(
                roadmapUseCase.updateRoadmap(request.toUpdateCommand(id, authenticatedMember.memberId()))
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @LoginMember AuthenticatedMember authenticatedMember,
            @PathVariable Long id
    ) {
        roadmapUseCase.deleteRoadmap(id, authenticatedMember.memberId());
        return ResponseEntity.noContent().build();
    }
}
