package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.application.port.command.ChangeMemberStatusCommand;

public interface ChangeMemberStatusUseCase {

    AdminMemberResult changeStatus(ChangeMemberStatusCommand command);
}
