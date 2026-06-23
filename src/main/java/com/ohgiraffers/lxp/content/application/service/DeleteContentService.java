package com.ohgiraffers.lxp.content.application.service;

import com.ohgiraffers.lxp.content.application.port.in.DeleteContentUseCase;
import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteContentService implements DeleteContentUseCase {

    private final ContentRepositoryPort contentRepository;

    public DeleteContentService(ContentRepositoryPort contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public void delete(Long contentId) {
        contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        contentRepository.delete(contentId);
    }
}
