package com.ohgiraffers.lxp.auth.application.port.in;

import com.ohgiraffers.lxp.auth.application.port.command.LogoutCommand;

public interface LogoutUseCase {

    void logout(LogoutCommand command);
}
