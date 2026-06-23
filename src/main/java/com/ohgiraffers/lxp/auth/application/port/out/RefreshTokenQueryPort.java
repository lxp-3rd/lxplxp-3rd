package com.ohgiraffers.lxp.auth.application.port.out;

import com.ohgiraffers.lxp.auth.application.dto.RefreshTokenInfo;

import java.util.Optional;

public interface RefreshTokenQueryPort {

    Optional<RefreshTokenInfo> findByToken(String refreshToken);
}
