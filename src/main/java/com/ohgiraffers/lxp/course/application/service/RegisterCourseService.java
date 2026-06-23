package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.InstructorValidationPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterCourseService implements RegisterCourseUseCase {

    private final CourseRepositoryPort courseRepository;
    private final InstructorValidationPort instructorValidation;

    public RegisterCourseService(CourseRepositoryPort courseRepository,
                                  InstructorValidationPort instructorValidation) {
        this.courseRepository = courseRepository;
        this.instructorValidation = instructorValidation;
    }

    @Override
    public Long register(RegisterCourseCommand command) {
        if (!instructorValidation.isActiveInstructor(command.instructorId())) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND);
        }

        try {
            Course course = Course.create(
                    command.instructorId(),
                    command.title(),
                    command.description(),
                    command.thumbnailUrl()
            );
            return courseRepository.save(course).getId();
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }
}
