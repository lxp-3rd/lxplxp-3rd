package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberCommand;

import java.util.List;

public interface GetAdminMemberListUseCase {

    List<AdminMemberResult> getMembers(AdminMemberCommand command);
}
