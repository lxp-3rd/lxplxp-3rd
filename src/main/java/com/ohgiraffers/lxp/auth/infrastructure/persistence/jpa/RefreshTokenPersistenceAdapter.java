package com.ohgiraffers.lxp.auth.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenSavePort;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class RefreshTokenPersistenceAdapter implements RefreshTokenSavePort {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    public RefreshTokenPersistenceAdapter(RefreshTokenJpaRepository refreshTokenJpaRepository) {
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
    }

    @Override
    public void save(Long memberId, String refreshToken, Instant expiresAt) {
        refreshTokenJpaRepository.save(RefreshTokenJpaEntity.create(memberId, refreshToken, expiresAt));
    }
}
