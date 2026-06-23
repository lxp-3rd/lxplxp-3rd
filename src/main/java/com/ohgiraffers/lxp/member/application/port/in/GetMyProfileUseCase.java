package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.MemberProfileResult;

public interface GetMyProfileUseCase {

    MemberProfileResult getMyProfile(Long memberId);
}
