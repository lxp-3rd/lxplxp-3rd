package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.LikeCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.LikeCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class LikeCourseService implements LikeCourseUseCase {

    private final CourseLikeRepositoryPort courseLikeRepository;

    public LikeCourseService(CourseLikeRepositoryPort courseLikeRepository) {
        this.courseLikeRepository = courseLikeRepository;
    }

    @Override
    public void like(LikeCourseCommand command) {
        if (courseLikeRepository.existsByCourseIdAndLearnerId(command.courseId(), command.learnerId())) {
            throw new BusinessException(ErrorCode.COURSE_LIKE_ALREADY_EXISTS);
        }
        CourseLike like;
        try {
            like = CourseLike.create(command.courseId(), command.learnerId());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        courseLikeRepository.save(like);
    }
}
