package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.UnlikeCourseCommand;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UnlikeCourseServiceTest {

    @InjectMocks
    private UnlikeCourseService unlikeCourseService;

    @Mock
    private CourseLikeRepositoryPort courseLikeRepository;

    @Test
    @DisplayName("좋아요 취소 성공 시 해당 id로 삭제가 호출된다")
    void unlike_success() {
        CourseLike existing = CourseLike.restore(10L, 1L, 100L, null);
        UnlikeCourseCommand command = new UnlikeCourseCommand(1L, 100L);
        given(courseLikeRepository.findByCourseIdAndLearnerId(1L, 100L)).willReturn(Optional.of(existing));

        unlikeCourseService.unlike(command);

        then(courseLikeRepository).should().delete(10L);
    }

    @Test
    @DisplayName("좋아요 내역이 없으면 COURSE_LIKE_NOT_FOUND 예외가 발생한다")
    void unlike_notFound_throwsException() {
        UnlikeCourseCommand command = new UnlikeCourseCommand(1L, 100L);
        given(courseLikeRepository.findByCourseIdAndLearnerId(1L, 100L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> unlikeCourseService.unlike(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_LIKE_NOT_FOUND.getMessage());
    }
}
