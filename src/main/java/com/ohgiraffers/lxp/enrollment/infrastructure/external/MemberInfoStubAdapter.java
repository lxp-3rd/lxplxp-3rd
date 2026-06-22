package com.ohgiraffers.lxp.enrollment.infrastructure.external;

import com.ohgiraffers.lxp.enrollment.application.dto.MemberInfo;
import com.ohgiraffers.lxp.enrollment.application.dto.Role;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadMemberInfoPort;
import org.springframework.stereotype.Component;

/**
 * LoadMemberInfoPort 스텁. member 도메인 미구현 상태에서 항상 유효한 수강생(정지 아님)을 응답한다.
 * TODO: member 도메인 구현 후 실제 조회 어댑터로 교체.
 */
@Component
public class MemberInfoStubAdapter implements LoadMemberInfoPort {

    @Override
    public MemberInfo load(Long memberId) {
        return new MemberInfo(memberId, Role.LEARNER, false);
    }
}
