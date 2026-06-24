package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.ChangeCourseStatusCommand;
import com.ohgiraffers.lxp.course.application.port.in.ChangeCourseStatusUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangeCourseStatusService implements ChangeCourseStatusUseCase {

    private final CourseRepositoryPort courseRepository;

    public ChangeCourseStatusService(CourseRepositoryPort courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void changeStatus(ChangeCourseStatusCommand command) {
        Course course = courseRepository.findById(command.courseId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
        try {
            course.changeStatus(command.newStatus(), command.changedBy());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.COURSE_STATUS_CHANGE_NOT_ALLOWED);
        }
        courseRepository.save(course);
    }
}
