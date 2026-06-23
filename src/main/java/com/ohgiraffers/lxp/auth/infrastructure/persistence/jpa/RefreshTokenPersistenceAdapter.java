package com.ohgiraffers.lxp.auth.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.auth.application.dto.RefreshTokenInfo;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenQueryPort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenRevokePort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenSavePort;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class RefreshTokenPersistenceAdapter implements RefreshTokenSavePort, RefreshTokenQueryPort, RefreshTokenRevokePort {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    public RefreshTokenPersistenceAdapter(RefreshTokenJpaRepository refreshTokenJpaRepository) {
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
    }

    @Override
    public void save(Long memberId, String refreshToken, Instant expiresAt) {
        String tokenHash = RefreshTokenHashProvider.hash(refreshToken);
        refreshTokenJpaRepository.save(RefreshTokenJpaEntity.from(memberId, tokenHash, expiresAt));
    }

    @Override
    public Optional<RefreshTokenInfo> findByToken(String refreshToken) {
        String tokenHash = RefreshTokenHashProvider.hash(refreshToken);
        return refreshTokenJpaRepository.findByTokenHashAndDeletedAtIsNull(tokenHash)
                .map(RefreshTokenJpaEntity::toDomain);
    }

    @Override
    public void revoke(String refreshToken) {
        String tokenHash = RefreshTokenHashProvider.hash(refreshToken);
        refreshTokenJpaRepository.findByTokenHashAndDeletedAtIsNull(tokenHash)
                .ifPresent(RefreshTokenJpaEntity::revoke);
    }
}
