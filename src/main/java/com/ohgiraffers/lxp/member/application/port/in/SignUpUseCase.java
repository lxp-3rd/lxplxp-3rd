package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
import com.ohgiraffers.lxp.member.application.port.command.SignUpCommand;

public interface SignUpUseCase {

    SignUpResult signUp(SignUpCommand command);
}
