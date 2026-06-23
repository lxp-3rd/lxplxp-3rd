package com.ohgiraffers.lxp.content.application.port.in;

import com.ohgiraffers.lxp.content.application.port.command.UpdateContentCommand;

public interface UpdateContentUseCase {
    void update(UpdateContentCommand command);
}
