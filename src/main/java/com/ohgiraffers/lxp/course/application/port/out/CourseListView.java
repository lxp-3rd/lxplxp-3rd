package com.ohgiraffers.lxp.course.application.port.out;

/**
 * Course 컨텍스트가 제공하는 강좌 목록 행. instructorId로 강사명을 따로 조합한다.
 */
public record CourseListView(
        Long id,
        String title,
        String thumbnailUrl,
        Long instructorId
) {}
