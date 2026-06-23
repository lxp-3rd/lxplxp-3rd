package com.ohgiraffers.lxp.content.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentJpaRepository extends JpaRepository<ContentJpaEntity, Long> {
}
