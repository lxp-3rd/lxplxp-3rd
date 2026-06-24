package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.InstructorValidationPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterCourseService implements RegisterCourseUseCase {

    private final CourseRepositoryPort courseRepository;
    private final ContentRepositoryPort contentRepository;
    private final InstructorValidationPort instructorValidation;

    public RegisterCourseService(CourseRepositoryPort courseRepository,
                                  ContentRepositoryPort contentRepository,
                                  InstructorValidationPort instructorValidation) {
        this.courseRepository = courseRepository;
        this.contentRepository = contentRepository;
        this.instructorValidation = instructorValidation;
    }

    @Override
    public Long register(RegisterCourseCommand command) {
        if (!instructorValidation.isActiveInstructor(command.instructorId())) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND);
        }

        Course course;
        try {
            course = Course.create(
                    command.instructorId(),
                    command.title(),
                    command.description(),
                    command.thumbnailUrl()
            );
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        Course savedCourse = courseRepository.save(course);
        saveCurriculum(savedCourse.getId(), command.contents());
        return savedCourse.getId();
    }

    private void saveCurriculum(Long courseId, List<String> contents) {
        if (contents == null || contents.isEmpty()) {
            return;
        }

        for (int i = 0; i < contents.size(); i++) {
            String title = contents.get(i);
            if (title == null || title.isBlank()) {
                throw new BusinessException(ErrorCode.INVALID_INPUT);
            }

            try {
                contentRepository.save(Content.create(courseId, i + 1, title.trim(), "about:blank"));
            } catch (IllegalArgumentException e) {
                throw new BusinessException(ErrorCode.INVALID_INPUT);
            }
        }
    }
}
