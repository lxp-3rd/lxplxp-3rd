package com.ohgiraffers.lxp.roadmap.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.command.CreateRoadmapCommand;
import com.ohgiraffers.lxp.roadmap.application.port.command.UpdateRoadmapCommand;
import com.ohgiraffers.lxp.roadmap.application.port.out.CoursePort;
import com.ohgiraffers.lxp.roadmap.application.port.out.RoadmapRepositoryPort;
import com.ohgiraffers.lxp.roadmap.domain.model.entity.Roadmap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class RoadmapServiceTest {

    @InjectMocks
    private RoadmapService roadmapService;

    @Mock
    private RoadmapRepositoryPort roadmapRepositoryPort;

    @Mock
    private CoursePort coursePort;

    @Test
    @DisplayName("로드맵 생성 성공 시 강좌 존재를 검증하고 저장한다")
    void createRoadmap_success() {
        CreateRoadmapCommand command = command();
        Roadmap saved = Roadmap.restore(
                1L, command.memberId(), command.name(), command.introduction(), command.courseIds(), null, null
        );
        given(coursePort.existsAllByIds(command.courseIds())).willReturn(true);
        given(roadmapRepositoryPort.save(any(Roadmap.class))).willReturn(saved);

        RoadmapResult result = roadmapService.createRoadmap(command);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.courseIds()).containsExactly(1L, 2L);
        then(roadmapRepositoryPort).should().save(any(Roadmap.class));
    }

    @Test
    @DisplayName("존재하지 않는 강좌가 있으면 로드맵 생성에 실패한다")
    void createRoadmap_courseNotFound() {
        CreateRoadmapCommand command = command();
        given(coursePort.existsAllByIds(command.courseIds())).willReturn(false);

        assertThatThrownBy(() -> roadmapService.createRoadmap(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.COURSE_NOT_FOUND);

        then(roadmapRepositoryPort).should(never()).save(any());
    }

    @Test
    @DisplayName("로드맵 수정 성공 시 기존 로드맵을 조회하고 저장한다")
    void updateRoadmap_success() {
        UpdateRoadmapCommand command = new UpdateRoadmapCommand(
                1L, 1L, "수정 로드맵", "수정된 강좌 로드맵 소개 문장입니다.", List.of(2L, 3L)
        );
        Roadmap existing = Roadmap.restore(
                1L, 1L, "기존 로드맵", "기존 강좌 로드맵 소개 문장을 충분히 작성합니다.", List.of(1L, 2L), null, null
        );
        Roadmap saved = Roadmap.restore(
                1L, command.memberId(), command.name(), command.introduction(), command.courseIds(), null, null
        );
        given(coursePort.existsAllByIds(command.courseIds())).willReturn(true);
        given(roadmapRepositoryPort.findById(1L)).willReturn(Optional.of(existing));
        given(roadmapRepositoryPort.save(any(Roadmap.class))).willReturn(saved);

        RoadmapResult result = roadmapService.updateRoadmap(command);

        assertThat(result.name()).isEqualTo("수정 로드맵");
        assertThat(result.courseIds()).containsExactly(2L, 3L);
    }

    @Test
    @DisplayName("존재하지 않는 로드맵 조회 시 예외가 발생한다")
    void getRoadmap_notFound() {
        given(roadmapRepositoryPort.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> roadmapService.getRoadmap(99L, 1L))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.ROADMAP_NOT_FOUND);
    }

    @Test
    @DisplayName("다른 회원이 로드맵을 수정하면 예외가 발생한다")
    void updateRoadmap_forbidden() {
        UpdateRoadmapCommand command = new UpdateRoadmapCommand(
                1L, 2L, "수정 로드맵", "수정된 강좌 로드맵 소개 문장입니다.", List.of(2L, 3L)
        );
        Roadmap existing = Roadmap.restore(
                1L, 1L, "기존 로드맵", "기존 강좌 로드맵 소개 문장을 충분히 작성합니다.", List.of(1L, 2L), null, null
        );
        given(coursePort.existsAllByIds(command.courseIds())).willReturn(true);
        given(roadmapRepositoryPort.findById(1L)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> roadmapService.updateRoadmap(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.FORBIDDEN);

        then(roadmapRepositoryPort).should(never()).save(any());
    }

    @Test
    @DisplayName("다른 회원이 로드맵을 삭제하면 예외가 발생한다")
    void deleteRoadmap_forbidden() {
        Roadmap existing = Roadmap.restore(
                1L, 1L, "기존 로드맵", "기존 강좌 로드맵 소개 문장을 충분히 작성합니다.", List.of(1L, 2L), null, null
        );
        given(roadmapRepositoryPort.findById(1L)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> roadmapService.deleteRoadmap(1L, 2L))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.FORBIDDEN);

        then(roadmapRepositoryPort).should(never()).deleteById(1L);
    }

    private CreateRoadmapCommand command() {
        return new CreateRoadmapCommand(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        );
    }
}
