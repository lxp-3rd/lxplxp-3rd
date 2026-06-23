package com.ohgiraffers.lxp.auth.application.port.out;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;

public interface AccessTokenIssuePort {

    String issueAccessToken(Long memberId, MemberRole role);
}
