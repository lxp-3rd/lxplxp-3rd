package com.ohgiraffers.lxp.roadmap.presentation.web;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMember;
import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.in.ParticipatingRoadmapUseCase;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/roadmaps")
public class RoadmapController {

    private final RoadmapUseCase roadmapUseCase;
    private final ParticipatingRoadmapUseCase participatingRoadmapUseCase;

    public RoadmapController(
            RoadmapUseCase roadmapUseCase,
            ParticipatingRoadmapUseCase participatingRoadmapUseCase
    ) {
        this.roadmapUseCase = roadmapUseCase;
        this.participatingRoadmapUseCase = participatingRoadmapUseCase;
    }

    @GetMapping
    public ResponseEntity<List<RoadmapResponse>> getAll() {
        return ResponseEntity.ok(roadmapUseCase.getAllRoadmaps()
                .stream()
                .map(RoadmapResponse::from)
                .toList());
    }

    @PostMapping
    public ResponseEntity<RoadmapResponse> create(
            @LoginMember AuthenticatedMember authenticatedMember,
            @RequestBody @Valid RoadmapRequest request
    ) {
        RoadmapResult result = roadmapUseCase.createRoadmap(request.toCreateCommand(authenticatedMember.memberId()));
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(RoadmapResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoadmapResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(RoadmapResponse.from(roadmapUseCase.getRoadmap(id)));
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoadmapResponse>> getAvailable(@LoginMember AuthenticatedMember authenticatedMember) {
        return ResponseEntity.ok(roadmapUseCase.getAvailableRoadmaps(authenticatedMember.memberId())
                .stream()
                .map(RoadmapResponse::from)
                .toList());
    }

    @GetMapping("/participating")
    public ResponseEntity<List<RoadmapResponse>> getParticipating(
            @LoginMember AuthenticatedMember authenticatedMember
    ) {
        return ResponseEntity.ok(participatingRoadmapUseCase.getParticipatingRoadmaps(authenticatedMember.memberId())
                .stream()
                .map(RoadmapResponse::from)
                .toList());
    }

    @GetMapping("/created")
    public ResponseEntity<List<RoadmapResponse>> getCreated(@LoginMember AuthenticatedMember authenticatedMember) {
        return ResponseEntity.ok(roadmapUseCase.getCreatedRoadmaps(authenticatedMember.memberId())
                .stream()
                .map(RoadmapResponse::from)
                .toList());
    }

    @PostMapping("/{id}/participate")
    public ResponseEntity<Void> participate(
            @LoginMember AuthenticatedMember authenticatedMember,
            @PathVariable Long id
    ) {
        participatingRoadmapUseCase.participate(id, authenticatedMember.memberId());
        return ResponseEntity.noContent().build();
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
