package com.ohgiraffers.lxp.member.presentation.web;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMember;
import com.ohgiraffers.lxp.member.application.port.command.ChangePasswordCommand;
import com.ohgiraffers.lxp.member.application.port.command.WithdrawMemberCommand;
import com.ohgiraffers.lxp.member.application.port.in.ChangePasswordUseCase;
import com.ohgiraffers.lxp.member.application.port.in.WithdrawMemberUseCase;
import com.ohgiraffers.lxp.member.presentation.dto.ChangePasswordRequest;
import com.ohgiraffers.lxp.member.presentation.dto.WithdrawMemberRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/me")
public class MemberAccountController {

    private final ChangePasswordUseCase changePasswordUseCase;
    private final WithdrawMemberUseCase withdrawMemberUseCase;

    public MemberAccountController(
            ChangePasswordUseCase changePasswordUseCase,
            WithdrawMemberUseCase withdrawMemberUseCase
    ) {
        this.changePasswordUseCase = changePasswordUseCase;
        this.withdrawMemberUseCase = withdrawMemberUseCase;
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @LoginMember AuthenticatedMember authenticatedMember,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        changePasswordUseCase.changePassword(new ChangePasswordCommand(
                authenticatedMember.memberId(),
                request.currentPassword(),
                request.newPassword(),
                request.newPasswordConfirm()
        ));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(
            @LoginMember AuthenticatedMember authenticatedMember,
            @Valid @RequestBody WithdrawMemberRequest request
    ) {
        withdrawMemberUseCase.withdraw(new WithdrawMemberCommand(
                authenticatedMember.memberId(),
                request.password()
        ));
        return ResponseEntity.noContent().build();
    }
}
