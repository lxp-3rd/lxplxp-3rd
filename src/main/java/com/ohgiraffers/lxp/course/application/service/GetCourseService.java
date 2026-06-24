package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;
import com.ohgiraffers.lxp.course.application.port.in.GetCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetCourseService implements GetCourseUseCase {

    private final CourseRepositoryPort courseRepository;
    private final CourseLikeRepositoryPort courseLikeRepository;

    public GetCourseService(CourseRepositoryPort courseRepository,
                            CourseLikeRepositoryPort courseLikeRepository) {
        this.courseRepository = courseRepository;
        this.courseLikeRepository = courseLikeRepository;
    }

    @Override
    public CourseResult getCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
        long likeCount = courseLikeRepository.countByCourseId(courseId);
        return new CourseResult(
                course.getId(),
                course.getInstructorId(),
                course.getTitle(),
                course.getDescription(),
                course.getThumbnailUrl(),
                course.getStatus(),
                course.getHiddenBy(),
                likeCount
        );
    }
}
