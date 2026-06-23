package com.ohgiraffers.lxp.auth.application.port.out;

public interface RefreshTokenRevokePort {

    void revoke(String refreshToken);
}
