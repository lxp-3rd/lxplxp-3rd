package com.ohgiraffers.lxp.announcement.domain.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

public class AnnouncementNotFoundException extends BusinessException {

    public AnnouncementNotFoundException() {
        super(ErrorCode.ANNOUNCEMENT_NOT_FOUND);
    }
}