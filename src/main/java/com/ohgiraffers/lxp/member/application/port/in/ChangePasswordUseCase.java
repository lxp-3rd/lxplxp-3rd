package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.port.command.ChangePasswordCommand;

public interface ChangePasswordUseCase {

    void changePassword(ChangePasswordCommand command);
}
