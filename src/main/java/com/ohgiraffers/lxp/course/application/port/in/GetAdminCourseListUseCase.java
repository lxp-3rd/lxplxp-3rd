package com.ohgiraffers.lxp.course.application.port.in;

import java.util.List;

import com.ohgiraffers.lxp.course.application.dto.AdminCourseResult;

public interface GetAdminCourseListUseCase {

    /**
     * 삭제되지 않은 모든 강좌(상태 무관)를 관리자 화면용으로 조회한다.
     */
    List<AdminCourseResult> getAdminCourseList();
}