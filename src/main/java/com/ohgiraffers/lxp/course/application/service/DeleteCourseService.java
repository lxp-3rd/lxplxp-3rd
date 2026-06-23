package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.in.DeleteCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteCourseService implements DeleteCourseUseCase {

    private final CourseRepositoryPort courseRepository;

    public DeleteCourseService(CourseRepositoryPort courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void delete(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
        courseRepository.delete(courseId);
    }
}
