package com.ohgiraffers.lxp.announcement.presentation.dto;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAnnouncementRequest(
        @NotNull
        Long adminId,

        @NotBlank
        @Size(min = 2, max = 100)
        String title,

        @NotBlank
        String content,

        @NotNull
        AnnouncementStatus status
) {}