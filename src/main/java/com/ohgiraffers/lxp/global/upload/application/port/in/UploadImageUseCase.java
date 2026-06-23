package com.ohgiraffers.lxp.global.upload.application.port.in;

import com.ohgiraffers.lxp.global.upload.application.dto.ImageUploadResult;
import com.ohgiraffers.lxp.global.upload.application.port.command.UploadImageCommand;

public interface UploadImageUseCase {

    ImageUploadResult upload(UploadImageCommand command);
}
