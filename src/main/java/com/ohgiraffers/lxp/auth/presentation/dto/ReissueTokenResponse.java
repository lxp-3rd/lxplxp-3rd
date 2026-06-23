package com.ohgiraffers.lxp.auth.presentation.dto;

import com.ohgiraffers.lxp.auth.application.dto.ReissueTokenResult;

public record ReissueTokenResponse(
        String accessToken
) {

    public static ReissueTokenResponse from(ReissueTokenResult result) {
        return new ReissueTokenResponse(result.accessToken());
    }
}
