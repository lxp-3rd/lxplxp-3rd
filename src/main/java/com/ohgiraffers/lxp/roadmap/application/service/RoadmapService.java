package com.ohgiraffers.lxp.roadmap.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.command.CreateRoadmapCommand;
import com.ohgiraffers.lxp.roadmap.application.port.command.UpdateRoadmapCommand;
import com.ohgiraffers.lxp.roadmap.application.port.in.RoadmapUseCase;
import com.ohgiraffers.lxp.roadmap.application.port.out.CoursePort;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapRepositoryPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoadmapService implements RoadmapUseCase {

    private final RoadmapRepositoryPort roadmapRepositoryPort;
    private final CoursePort coursePort;

    public RoadmapService(RoadmapRepositoryPort roadmapRepositoryPort, CoursePort coursePort) {
        this.roadmapRepositoryPort = roadmapRepositoryPort;
        this.coursePort = coursePort;
    }

    @Override
    @Transactional
    public RoadmapResult createRoadmap(CreateRoadmapCommand command) {
        validateCourses(command.courseIds());
        Roadmap roadmap = Roadmap.create(command.memberId(), command.name(), command.introduction(), command.courseIds());
        return RoadmapResult.from(roadmapRepositoryPort.save(roadmap));
    }

    @Override
    public RoadmapResult getRoadmap(Long roadmapId, Long memberId) {
        Roadmap roadmap = getRoadmapOrThrow(roadmapId);
        validateOwner(roadmap, memberId);
        return RoadmapResult.from(roadmap);
    }

    @Override
    public List<RoadmapResult> getRoadmaps(Long memberId) {
        return roadmapRepositoryPort.findAllByMemberId(memberId)
                .stream()
                .map(RoadmapResult::from)
                .toList();
    }

    @Override
    @Transactional
    public RoadmapResult updateRoadmap(UpdateRoadmapCommand command) {
        validateCourses(command.courseIds());
        Roadmap existing = getRoadmapOrThrow(command.roadmapId());
        validateOwner(existing, command.memberId());
        Roadmap roadmap = existing.update(command.name(), command.introduction(), command.courseIds());
        return RoadmapResult.from(roadmapRepositoryPort.save(roadmap));
    }

    @Override
    @Transactional
    public void deleteRoadmap(Long roadmapId, Long memberId) {
        Roadmap roadmap = getRoadmapOrThrow(roadmapId);
        validateOwner(roadmap, memberId);
        roadmapRepositoryPort.deleteById(roadmapId);
    }

    private Roadmap getRoadmapOrThrow(Long roadmapId) {
        return roadmapRepositoryPort.findById(roadmapId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROADMAP_NOT_FOUND));
    }

    private void validateCourses(List<Long> courseIds) {
        if (!coursePort.existsAllByIds(courseIds)) {
            throw new BusinessException(ErrorCode.COURSE_NOT_FOUND);
        }
    }

    private void validateOwner(Roadmap roadmap, Long memberId) {
        if (!roadmap.isOwnedBy(memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
