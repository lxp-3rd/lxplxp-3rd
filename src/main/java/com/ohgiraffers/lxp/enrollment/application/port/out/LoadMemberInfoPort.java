package com.ohgiraffers.lxp.enrollment.application.port.out;

import com.ohgiraffers.lxp.enrollment.application.dto.MemberInfo;

public interface LoadMemberInfoPort {

    MemberInfo load(Long memberId);

}
