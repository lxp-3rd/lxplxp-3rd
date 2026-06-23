package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.LoginResult;
import com.ohgiraffers.lxp.member.application.port.command.LoginCommand;

public interface LoginUseCase {

    LoginResult login(LoginCommand command);
}
