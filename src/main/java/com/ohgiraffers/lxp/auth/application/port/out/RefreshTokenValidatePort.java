package com.ohgiraffers.lxp.auth.application.port.out;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;

public interface RefreshTokenValidatePort {

    AuthenticatedMember validateRefreshToken(String token);
}
