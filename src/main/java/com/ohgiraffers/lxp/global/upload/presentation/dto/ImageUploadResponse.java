package com.ohgiraffers.lxp.global.upload.presentation.dto;

import com.ohgiraffers.lxp.global.upload.application.dto.ImageUploadResult;

public record ImageUploadResponse(String imageUrl) {

    public static ImageUploadResponse from(ImageUploadResult result) {
        return new ImageUploadResponse(result.imageUrl());
    }
}