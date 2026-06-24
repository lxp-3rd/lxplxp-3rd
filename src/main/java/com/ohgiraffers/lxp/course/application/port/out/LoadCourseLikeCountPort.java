package com.ohgiraffers.lxp.course.application.port.out;

import java.util.List;
import java.util.Map;

/**
 * 강좌별 좋아요 수를 batch로 집계한다(N+1 금지).
 */
public interface LoadCourseLikeCountPort {

    /**
     * @return key=courseId, value=좋아요 수. 좋아요가 없는 강좌는 결과에 포함되지 않을 수 있다.
     */
    Map<Long, Long> countByCourseIds(List<Long> courseIds);
}
