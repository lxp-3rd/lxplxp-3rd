package com.ohgiraffers.lxp.auth.presentation.web;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.dto.ReissueTokenResult;
import com.ohgiraffers.lxp.auth.application.port.in.LogoutUseCase;
import com.ohgiraffers.lxp.auth.application.port.in.ReissueTokenUseCase;
import com.ohgiraffers.lxp.auth.presentation.dto.LoginRequest;
import com.ohgiraffers.lxp.auth.presentation.dto.LoginResponse;
import com.ohgiraffers.lxp.auth.presentation.dto.LogoutRequest;
import com.ohgiraffers.lxp.auth.presentation.dto.ReissueTokenRequest;
import com.ohgiraffers.lxp.auth.presentation.dto.ReissueTokenResponse;
import com.ohgiraffers.lxp.auth.presentation.dto.SignUpRequest;
import com.ohgiraffers.lxp.auth.presentation.dto.SignUpResponse;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMember;
import com.ohgiraffers.lxp.member.application.dto.LoginResult;
import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
import com.ohgiraffers.lxp.member.application.port.in.LoginUseCase;
import com.ohgiraffers.lxp.member.application.port.in.SignUpUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;
    private final ReissueTokenUseCase reissueTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    public AuthController(
            SignUpUseCase signUpUseCase,
            LoginUseCase loginUseCase,
            ReissueTokenUseCase reissueTokenUseCase,
            LogoutUseCase logoutUseCase
    ) {
        this.signUpUseCase = signUpUseCase;
        this.loginUseCase = loginUseCase;
        this.reissueTokenUseCase = reissueTokenUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResult result = signUpUseCase.signUp(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SignUpResponse.from(result));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = loginUseCase.login(request.toCommand());
        return ResponseEntity.ok(LoginResponse.from(result));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokenResponse> reissue(@Valid @RequestBody ReissueTokenRequest request) {
        ReissueTokenResult result = reissueTokenUseCase.reissue(request.toCommand());
        return ResponseEntity.ok(ReissueTokenResponse.from(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @LoginMember AuthenticatedMember authenticatedMember,
            @Valid @RequestBody LogoutRequest request
    ) {
        logoutUseCase.logout(request.toCommand(authenticatedMember.memberId()));
        return ResponseEntity.noContent().build();
    }
}
