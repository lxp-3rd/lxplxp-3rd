package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberDetailCommand;

public interface GetAdminMemberDetailUseCase {

    AdminMemberResult getMember(AdminMemberDetailCommand command);
}
