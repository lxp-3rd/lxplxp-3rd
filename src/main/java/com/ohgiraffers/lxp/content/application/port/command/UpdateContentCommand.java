package com.ohgiraffers.lxp.content.application.port.command;

public record UpdateContentCommand(Long courseId, Long contentId, String title, String contentUrl) {}
