package com.ohgiraffers.lxp.content.application.service;

import com.ohgiraffers.lxp.content.application.port.command.UpdateContentCommand;
import com.ohgiraffers.lxp.content.application.port.in.UpdateContentUseCase;
import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateContentService implements UpdateContentUseCase {

    private final ContentRepositoryPort contentRepository;

    public UpdateContentService(ContentRepositoryPort contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public void update(UpdateContentCommand command) {
        Content content = contentRepository.findById(command.contentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        try {
            content.update(command.title(), command.contentUrl());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        contentRepository.save(content);
    }
}
