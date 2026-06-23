package com.ohgiraffers.lxp.content.presentation.dto;

import com.ohgiraffers.lxp.content.application.port.command.RegisterContentCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record RegisterContentRequest(
        @NotNull @Min(0) Integer sequence,
        @NotBlank String title,
        @NotBlank @URL String contentUrl
) {
    public RegisterContentCommand toCommand(Long courseId) {
        return new RegisterContentCommand(courseId, sequence, title, contentUrl);
    }
}
