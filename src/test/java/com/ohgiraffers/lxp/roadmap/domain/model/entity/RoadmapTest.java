package com.ohgiraffers.lxp.roadmap.domain.model.entity;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoadmapTest {

    @Test
    @DisplayName("로드맵을 생성하면 필드가 올바르게 세팅된다")
    void create() {
        Roadmap roadmap = Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        );

        assertThat(roadmap.getId()).isNull();
        assertThat(roadmap.getMemberId()).isEqualTo(1L);
        assertThat(roadmap.getName()).isEqualTo("백엔드 로드맵");
        assertThat(roadmap.getIntroduction()).isEqualTo("백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.");
        assertThat(roadmap.getCourseIds()).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("로드맵 명이 2자 미만이면 예외가 발생한다")
    void create_nameTooShort() {
        assertThatThrownBy(() -> Roadmap.create(
                1L,
                "백",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        ))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }

    @Test
    @DisplayName("로드맵 소개가 20자 미만이면 예외가 발생한다")
    void create_introductionTooShort() {
        assertThatThrownBy(() -> Roadmap.create(
                1L,
                "백엔드 로드맵",
                "짧은 소개",
                List.of(1L, 2L)
        ))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }

    @Test
    @DisplayName("강좌가 2개 미만이면 예외가 발생한다")
    void create_courseCountTooSmall() {
        assertThatThrownBy(() -> Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L)
        ))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }

    @Test
    @DisplayName("강좌 ID가 중복되면 예외가 발생한다")
    void create_duplicateCourseIds() {
        assertThatThrownBy(() -> Roadmap.create(
                1L,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 1L)
        ))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }
}
