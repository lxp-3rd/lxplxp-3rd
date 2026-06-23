package com.ohgiraffers.lxp.auth.application.port.out;

import com.ohgiraffers.lxp.auth.application.dto.TokenPair;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;

public interface TokenIssuePort {

    TokenPair issue(Long memberId, MemberRole role);
}
