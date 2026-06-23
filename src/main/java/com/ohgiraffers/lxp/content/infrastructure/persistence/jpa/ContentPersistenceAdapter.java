package com.ohgiraffers.lxp.content.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import org.springframework.stereotype.Repository;

@Repository
public class ContentPersistenceAdapter implements ContentRepositoryPort {

    private final ContentJpaRepository jpaRepository;

    public ContentPersistenceAdapter(ContentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Content save(Content content) {
        return jpaRepository.save(ContentJpaEntity.from(content)).toDomain();
    }
}
