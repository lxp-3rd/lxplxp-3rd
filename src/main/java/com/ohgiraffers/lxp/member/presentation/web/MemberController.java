package com.ohgiraffers.lxp.member.presentation.web;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMember;
import com.ohgiraffers.lxp.member.application.dto.MemberProfileResult;
import com.ohgiraffers.lxp.member.application.port.in.GetMyProfileUseCase;
import com.ohgiraffers.lxp.member.application.port.in.UpdateMyProfileUseCase;
import com.ohgiraffers.lxp.member.presentation.dto.MyProfileResponse;
import com.ohgiraffers.lxp.member.presentation.dto.UpdateMyProfileRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final GetMyProfileUseCase getMyProfileUseCase;
    private final UpdateMyProfileUseCase updateMyProfileUseCase;

    public MemberController(
            GetMyProfileUseCase getMyProfileUseCase,
            UpdateMyProfileUseCase updateMyProfileUseCase
    ) {
        this.getMyProfileUseCase = getMyProfileUseCase;
        this.updateMyProfileUseCase = updateMyProfileUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> getMyProfile(@LoginMember AuthenticatedMember authenticatedMember) {
        MemberProfileResult result = getMyProfileUseCase.getMyProfile(authenticatedMember.memberId());
        return ResponseEntity.ok(MyProfileResponse.from(result));
    }

    @PatchMapping("/me")
    public ResponseEntity<MyProfileResponse> updateMyProfile(
            @LoginMember AuthenticatedMember authenticatedMember,
            @Valid @RequestBody UpdateMyProfileRequest request
    ) {
        MemberProfileResult result = updateMyProfileUseCase.updateMyProfile(request.toCommand(authenticatedMember.memberId()));
        return ResponseEntity.ok(MyProfileResponse.from(result));
    }
}
