package com.ohgiraffers.lxp.global.upload.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.global.upload.application.dto.ImageUploadResult;
import com.ohgiraffers.lxp.global.upload.application.port.command.UploadImageCommand;
import com.ohgiraffers.lxp.global.upload.application.port.in.UploadImageUseCase;
import com.ohgiraffers.lxp.global.upload.application.port.out.ImageStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService implements UploadImageUseCase {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");

    private final ImageStoragePort imageStoragePort;

    @Override
    public ImageUploadResult upload(UploadImageCommand command) {
        if (command.fileData() == null || command.fileData().length == 0) {
            throw new BusinessException(ErrorCode.IMAGE_INVALID_FILE);
        }

        String extension = extractExtension(command.originalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new BusinessException(ErrorCode.IMAGE_INVALID_TYPE);
        }

        String filename = UUID.randomUUID() + "." + extension;
        imageStoragePort.save(command.fileData(), filename);

        return new ImageUploadResult("/images/" + filename);
    }

    private String extractExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new BusinessException(ErrorCode.IMAGE_INVALID_TYPE);
        }
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
    }
}
