package com.ohgiraffers.lxp.global.upload.application.port.command;

public record UploadImageCommand(byte[] fileData, String originalFilename) {
}
