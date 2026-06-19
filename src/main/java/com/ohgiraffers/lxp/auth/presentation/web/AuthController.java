package com.ohgiraffers.lxp.auth.presentation.web;

import com.ohgiraffers.lxp.auth.presentation.dto.SignUpRequest;
import com.ohgiraffers.lxp.auth.presentation.dto.SignUpResponse;
import com.ohgiraffers.lxp.common.model.ApiResponse;
import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
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

    public AuthController(SignUpUseCase signUpUseCase) {
        this.signUpUseCase = signUpUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResult result = signUpUseCase.signUp(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "MEMBER_SIGNUP_SUCCESS",
                        "회원가입이 완료되었습니다.",
                        SignUpResponse.from(result)
                ));
    }
}
