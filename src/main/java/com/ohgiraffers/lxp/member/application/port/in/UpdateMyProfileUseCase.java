package com.ohgiraffers.lxp.member.application.port.in;

import com.ohgiraffers.lxp.member.application.dto.MemberProfileResult;
import com.ohgiraffers.lxp.member.application.port.command.UpdateMyProfileCommand;

public interface UpdateMyProfileUseCase {

    MemberProfileResult updateMyProfile(UpdateMyProfileCommand command);
}
