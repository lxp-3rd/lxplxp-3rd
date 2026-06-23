package com.ohgiraffers.lxp.content.application.port.out;

import com.ohgiraffers.lxp.content.domain.model.entity.Content;

public interface ContentRepositoryPort {

    Content save(Content content);
}
