package com.ohgiraffers.lxp.global.upload.infrastructure.storage;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.global.upload.application.port.out.ImageStoragePort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalImageStorageAdapter implements ImageStoragePort {

    private static final String UPLOAD_DIR = "uploads/images";

    @Override
    public String save(byte[] fileData, String filename) {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        try {
            Files.createDirectories(uploadPath);
            Files.write(uploadPath.resolve(filename), fileData);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
        return filename;
    }
}