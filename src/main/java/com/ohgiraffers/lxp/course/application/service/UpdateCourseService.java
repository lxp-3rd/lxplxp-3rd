package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.UpdateCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.UpdateCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCourseService implements UpdateCourseUseCase {

    private final CourseRepositoryPort courseRepository;

    public UpdateCourseService(CourseRepositoryPort courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void update(UpdateCourseCommand command) {
        Course course = courseRepository.findById(command.courseId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
        try {
            course.update(command.title(), command.description(), command.thumbnailUrl());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        courseRepository.save(course);
    }
}
