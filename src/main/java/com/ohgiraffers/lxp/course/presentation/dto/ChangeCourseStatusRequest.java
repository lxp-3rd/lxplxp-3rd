package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.port.command.ChangeCourseStatusCommand;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;
import jakarta.validation.constraints.NotNull;

public record ChangeCourseStatusRequest(

        @NotNull(message = "변경할 상태는 필수입니다.")
        CourseStatus status,

        @NotNull(message = "변경 요청자 구분은 필수입니다.")
        HiddenBy changedBy
) {
    public ChangeCourseStatusCommand toCommand(Long courseId) {
        return new ChangeCourseStatusCommand(courseId, status, changedBy);
    }
}
