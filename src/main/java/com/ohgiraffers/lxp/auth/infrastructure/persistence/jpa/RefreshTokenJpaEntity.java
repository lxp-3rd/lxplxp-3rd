package com.ohgiraffers.lxp.auth.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

@Entity
@Table(
        name = "refresh_tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_refresh_tokens_token", columnNames = "token")
        }
)
public class RefreshTokenJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 512)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    protected RefreshTokenJpaEntity() {
    }

    private RefreshTokenJpaEntity(Long memberId, String token, Instant expiresAt) {
        this.memberId = memberId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.revoked = false;
    }

    public static RefreshTokenJpaEntity create(Long memberId, String token, Instant expiresAt) {
        return new RefreshTokenJpaEntity(memberId, token, expiresAt);
    }
}
