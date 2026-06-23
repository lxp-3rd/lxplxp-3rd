package com.ohgiraffers.lxp.global.upload.presentation.web;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.global.upload.application.port.command.UploadImageCommand;
import com.ohgiraffers.lxp.global.upload.application.port.in.UploadImageUseCase;
import com.ohgiraffers.lxp.global.upload.presentation.dto.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageUploadController {

    private final UploadImageUseCase uploadImageUseCase;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> upload(@RequestParam("image") MultipartFile file) {
        try {
            UploadImageCommand command = new UploadImageCommand(
                    file.getBytes(),
                    file.getOriginalFilename()
            );
            return ResponseEntity.ok(ImageUploadResponse.from(uploadImageUseCase.upload(command)));
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }
}