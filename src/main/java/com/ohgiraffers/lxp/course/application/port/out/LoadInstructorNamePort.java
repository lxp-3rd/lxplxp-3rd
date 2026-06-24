package com.ohgiraffers.lxp.course.application.port.out;

import java.util.List;
import java.util.Map;

/**
 * member 컨텍스트에서 강사(MemberRole=INSTRUCTOR) 표시 이름을 batch로 조회한다(N+1 금지).
 */
public interface LoadInstructorNamePort {

    /**
     * @return key=instructorId(memberId), value=강사 표시 이름. 강사가 아니거나 없으면 결과에 포함되지 않을 수 있다.
     */
    Map<Long, String> findNamesByInstructorIds(List<Long> instructorIds);
}
