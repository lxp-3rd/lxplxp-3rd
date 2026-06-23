package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;
import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetCourseListService implements GetCourseListUseCase {

    private final CourseRepositoryPort courseRepository;
    private final CourseLikeRepositoryPort courseLikeRepository;

    public GetCourseListService(CourseRepositoryPort courseRepository,
                                CourseLikeRepositoryPort courseLikeRepository) {
        this.courseRepository = courseRepository;
        this.courseLikeRepository = courseLikeRepository;
    }

    @Override
    public List<CourseResult> getCourseList() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseResult(
                        course.getId(),
                        course.getInstructorId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getThumbnailUrl(),
                        course.getStatus(),
                        course.getHiddenBy(),
                        courseLikeRepository.countByCourseId(course.getId())
                ))
                .toList();
    }
}
