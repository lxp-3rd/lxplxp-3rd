package com.ohgiraffers.lxp.content.application.service;

import com.ohgiraffers.lxp.content.application.port.command.RegisterContentCommand;
import com.ohgiraffers.lxp.content.application.port.in.RegisterContentUseCase;
import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.application.port.out.CourseValidationPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterContentService implements RegisterContentUseCase {

    private final ContentRepositoryPort contentRepository;
    private final CourseValidationPort courseValidation;

    public RegisterContentService(ContentRepositoryPort contentRepository,
                                  CourseValidationPort courseValidation) {
        this.contentRepository = contentRepository;
        this.courseValidation = courseValidation;
    }

    @Override
    public Long register(RegisterContentCommand command) {
        if (!courseValidation.existsById(command.courseId())) {
            throw new BusinessException(ErrorCode.COURSE_NOT_FOUND);
        }
        Content content;
        try {
            content = Content.create(
                    command.courseId(),
                    command.sequence(),
                    command.title(),
                    command.contentUrl()
            );
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        return contentRepository.save(content).getId();
    }
}
