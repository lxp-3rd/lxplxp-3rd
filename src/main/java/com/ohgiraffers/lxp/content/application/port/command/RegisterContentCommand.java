package com.ohgiraffers.lxp.content.application.port.command;

public record RegisterContentCommand(
        Long courseId,
        int sequence,
        String title,
        String contentUrl
) {}
