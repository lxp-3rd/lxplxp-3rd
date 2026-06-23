package com.ohgiraffers.lxp.auth.application.port.in;

import com.ohgiraffers.lxp.auth.application.dto.ReissueTokenResult;
import com.ohgiraffers.lxp.auth.application.port.command.ReissueTokenCommand;

public interface ReissueTokenUseCase {

    ReissueTokenResult reissue(ReissueTokenCommand command);
}
