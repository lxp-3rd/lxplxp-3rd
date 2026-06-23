package com.ohgiraffers.lxp.auth.application.port.out;

public interface AccessTokenIssuePort {

    String issueAccessToken(Long memberId, String role);
}
