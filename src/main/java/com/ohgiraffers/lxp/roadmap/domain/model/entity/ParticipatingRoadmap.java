package com.ohgiraffers.lxp.roadmap.domain.model.entity;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.roadmap.domain.model.vo.ParticipatingRoadmapStatus;

public class ParticipatingRoadmap {

    private final Long id;
    private final Long memberId;
    private final Long roadmapId;
    private final ParticipatingRoadmapStatus status;

    private ParticipatingRoadmap(Long id, Long memberId, Long roadmapId, ParticipatingRoadmapStatus status) {
        requireNonNull(memberId, "memberId");
        requireNonNull(roadmapId, "roadmapId");
        requireNonNull(status, "status");
        this.id = id;
        this.memberId = memberId;
        this.roadmapId = roadmapId;
        this.status = status;
    }

    public static ParticipatingRoadmap participate(Long memberId, Long roadmapId) {
        return new ParticipatingRoadmap(null, memberId, roadmapId, ParticipatingRoadmapStatus.ACTIVE);
    }

    public static ParticipatingRoadmap restore(
            Long id,
            Long memberId,
            Long roadmapId,
            ParticipatingRoadmapStatus status
    ) {
        return new ParticipatingRoadmap(id, memberId, roadmapId, status);
    }

    public boolean isParticipatedBy(Long memberId) {
        return this.memberId.equals(memberId);
    }

    private void requireNonNull(Object value, String field) {
        if (value == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public ParticipatingRoadmapStatus getStatus() {
        return status;
    }
}
