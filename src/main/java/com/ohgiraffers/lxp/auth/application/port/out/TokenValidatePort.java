package com.ohgiraffers.lxp.auth.application.port.out;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;

public interface TokenValidatePort {

    AuthenticatedMember validateAccessToken(String token);
}
