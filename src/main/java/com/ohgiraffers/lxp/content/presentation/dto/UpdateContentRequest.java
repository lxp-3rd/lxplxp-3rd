package com.ohgiraffers.lxp.content.presentation.dto;

import com.ohgiraffers.lxp.content.application.port.command.UpdateContentCommand;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UpdateContentRequest(
        @NotBlank String title,
        @NotBlank @URL String contentUrl
) {
    public UpdateContentCommand toCommand(Long courseId, Long contentId) {
        return new UpdateContentCommand(courseId, contentId, title, contentUrl);
    }
}
