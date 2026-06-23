package com.ohgiraffers.lxp.auth.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.auth.application.dto.RefreshTokenInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Import(RefreshTokenPersistenceAdapter.class)
class RefreshTokenPersistenceAdapterTest {

    @Autowired
    private RefreshTokenPersistenceAdapter refreshTokenPersistenceAdapter;

    @Autowired
    private RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Test
    void save_and_find_by_token_success() {
        Instant expiresAt = Instant.now().plusSeconds(3600);

        refreshTokenPersistenceAdapter.save(1L, "refresh-token", expiresAt);

        RefreshTokenInfo refreshTokenInfo = refreshTokenPersistenceAdapter.findByToken("refresh-token").orElseThrow();
        assertThat(refreshTokenInfo.memberId()).isEqualTo(1L);
        assertThat(refreshTokenInfo.expiresAt()).isEqualTo(expiresAt);
        assertThat(refreshTokenInfo.revoked()).isFalse();
        assertThat(refreshTokenJpaRepository.findByTokenHash("refresh-token")).isEmpty();
    }

    @Test
    void revoke_marks_refresh_token_revoked() {
        refreshTokenPersistenceAdapter.save(1L, "refresh-token", Instant.now().plusSeconds(3600));

        refreshTokenPersistenceAdapter.revoke("refresh-token");

        RefreshTokenInfo refreshTokenInfo = refreshTokenPersistenceAdapter.findByToken("refresh-token").orElseThrow();
        assertThat(refreshTokenInfo.revoked()).isTrue();
    }
}
