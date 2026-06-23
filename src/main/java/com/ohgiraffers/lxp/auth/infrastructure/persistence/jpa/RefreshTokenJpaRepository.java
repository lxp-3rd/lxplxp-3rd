package com.ohgiraffers.lxp.auth.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
}
