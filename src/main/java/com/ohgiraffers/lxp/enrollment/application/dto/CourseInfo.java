package com.ohgiraffers.lxp.enrollment.application.dto;

public record CourseInfo(Long courseId, boolean published) {

    public boolean isPublished() {
        return published;
    }
    
}
