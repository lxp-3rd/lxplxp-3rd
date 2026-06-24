package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.UnlikeCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.UnlikeCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class UnlikeCourseService implements UnlikeCourseUseCase {

    private final CourseLikeRepositoryPort courseLikeRepository;

    public UnlikeCourseService(CourseLikeRepositoryPort courseLikeRepository) {
        this.courseLikeRepository = courseLikeRepository;
    }

    @Override
    public void unlike(UnlikeCourseCommand command) {
        CourseLike like = courseLikeRepository.findByCourseIdAndLearnerId(command.courseId(), command.learnerId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_LIKE_NOT_FOUND));
        courseLikeRepository.delete(like.getId());
    }
}
