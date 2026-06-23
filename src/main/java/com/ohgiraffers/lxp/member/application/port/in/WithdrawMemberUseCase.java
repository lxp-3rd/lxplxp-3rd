package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.port.command.WithdrawMemberCommand;

public interface WithdrawMemberUseCase {

    void withdraw(WithdrawMemberCommand command);
}
