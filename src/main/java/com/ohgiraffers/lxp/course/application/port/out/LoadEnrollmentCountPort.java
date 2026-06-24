package com.ohgiraffers.lxp.course.application.port.out;

import java.util.List;
import java.util.Map;

/**
 * enrollment 컨텍스트의 강좌별 현재 수강 중(ACTIVE) 인원 수를 batch로 집계한다(N+1 금지).
 */
public interface LoadEnrollmentCountPort {

    /**
     * @return key=courseId, value=현재 수강 중(ACTIVE) 인원 수. 수강생이 없는 강좌는 결과에 포함되지 않을 수 있다.
     */
    Map<Long, Long> countByCourseIds(List<Long> courseIds);
}
