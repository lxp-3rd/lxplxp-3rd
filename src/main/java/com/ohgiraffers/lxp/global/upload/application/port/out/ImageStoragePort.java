package com.ohgiraffers.lxp.global.upload.application.port.out;

public interface ImageStoragePort {

    String save(byte[] fileData, String filename);
}