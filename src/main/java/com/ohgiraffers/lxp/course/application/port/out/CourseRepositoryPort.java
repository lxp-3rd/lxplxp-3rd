package com.ohgiraffers.lxp.course.application.port.out;

import com.ohgiraffers.lxp.course.domain.model.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepositoryPort {

    Course save(Course course);

    Optional<Course> findById(Long id);

    List<Course> findAll();

    void delete(Long id);
}
