package com.ohgiraffers.lxp.content.application.port.out;

import com.ohgiraffers.lxp.content.domain.model.entity.Content;

import java.util.Optional;

public interface ContentRepositoryPort {

    Content save(Content content);

    Optional<Content> findById(Long id);

    void delete(Long id);
}
