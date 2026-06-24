package com.ohgiraffers.lxp.instructor.presentation.dto;

import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;

public record MyInstructorResponse(Long id) {

    public static MyInstructorResponse from(Instructor instructor) {
        return new MyInstructorResponse(instructor.getId());
    }
}
