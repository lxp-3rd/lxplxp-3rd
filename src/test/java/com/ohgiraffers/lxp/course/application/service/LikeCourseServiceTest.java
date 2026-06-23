package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.LikeCourseCommand;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LikeCourseServiceTest {

    @InjectMocks
    private LikeCourseService likeCourseService;

    @Mock
    private CourseLikeRepositoryPort courseLikeRepository;

    @Test
    @DisplayName("좋아요 등록 성공 시 courseId와 learnerId가 저장된다")
    void like_success() {
        LikeCourseCommand command = new LikeCourseCommand(1L, 100L);
        given(courseLikeRepository.existsByCourseIdAndLearnerId(1L, 100L)).willReturn(false);
        given(courseLikeRepository.save(any(CourseLike.class))).willAnswer(inv -> inv.getArgument(0));

        likeCourseService.like(command);

        ArgumentCaptor<CourseLike> captor = ArgumentCaptor.forClass(CourseLike.class);
        then(courseLikeRepository).should().save(captor.capture());
        assertThat(captor.getValue().getCourseId()).isEqualTo(1L);
        assertThat(captor.getValue().getLearnerId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("이미 좋아요를 눌렀으면 COURSE_LIKE_ALREADY_EXISTS 예외가 발생한다")
    void like_alreadyExists_throwsException() {
        LikeCourseCommand command = new LikeCourseCommand(1L, 100L);
        given(courseLikeRepository.existsByCourseIdAndLearnerId(1L, 100L)).willReturn(true);

        assertThatThrownBy(() -> likeCourseService.like(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_LIKE_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("동시 요청으로 DB UNIQUE 제약 위반 시 COURSE_LIKE_ALREADY_EXISTS 예외가 발생한다")
    void like_concurrentDuplicate_throwsException() {
        LikeCourseCommand command = new LikeCourseCommand(1L, 100L);
        given(courseLikeRepository.existsByCourseIdAndLearnerId(1L, 100L)).willReturn(false);
        given(courseLikeRepository.save(any(CourseLike.class)))
                .willThrow(new DataIntegrityViolationException("duplicate key"));

        assertThatThrownBy(() -> likeCourseService.like(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_LIKE_ALREADY_EXISTS.getMessage());
    }
}
