package com.ohgiraffers.lxp.content.application.port.in;

import com.ohgiraffers.lxp.content.application.port.command.RegisterContentCommand;

public interface RegisterContentUseCase {

    Long register(RegisterContentCommand command);
}
