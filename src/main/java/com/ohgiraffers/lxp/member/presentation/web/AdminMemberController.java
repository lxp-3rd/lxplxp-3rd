package com.ohgiraffers.lxp.member.presentation.web;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.presentation.support.LoginMember;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberCommand;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberDetailCommand;
import com.ohgiraffers.lxp.member.application.port.command.ChangeMemberStatusCommand;
import com.ohgiraffers.lxp.member.application.port.in.ChangeMemberStatusUseCase;
import com.ohgiraffers.lxp.member.application.port.in.GetAdminMemberDetailUseCase;
import com.ohgiraffers.lxp.member.application.port.in.GetAdminMemberListUseCase;
import com.ohgiraffers.lxp.member.presentation.dto.AdminMemberResponse;
import com.ohgiraffers.lxp.member.presentation.dto.ChangeMemberStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final GetAdminMemberListUseCase getAdminMemberListUseCase;
    private final GetAdminMemberDetailUseCase getAdminMemberDetailUseCase;
    private final ChangeMemberStatusUseCase changeMemberStatusUseCase;

    public AdminMemberController(
            GetAdminMemberListUseCase getAdminMemberListUseCase,
            GetAdminMemberDetailUseCase getAdminMemberDetailUseCase,
            ChangeMemberStatusUseCase changeMemberStatusUseCase
    ) {
        this.getAdminMemberListUseCase = getAdminMemberListUseCase;
        this.getAdminMemberDetailUseCase = getAdminMemberDetailUseCase;
        this.changeMemberStatusUseCase = changeMemberStatusUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AdminMemberResponse>> getMembers(@LoginMember AuthenticatedMember authenticatedMember) {
        List<AdminMemberResponse> response = getAdminMemberListUseCase.getMembers(
                        new AdminMemberCommand(authenticatedMember.memberId(), authenticatedMember.role())
                ).stream()
                .map(AdminMemberResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<AdminMemberResponse> getMember(
            @LoginMember AuthenticatedMember authenticatedMember,
            @PathVariable Long memberId
    ) {
        AdminMemberResponse response = AdminMemberResponse.from(getAdminMemberDetailUseCase.getMember(
                new AdminMemberDetailCommand(authenticatedMember.memberId(), authenticatedMember.role(), memberId)
        ));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{memberId}/status")
    public ResponseEntity<AdminMemberResponse> changeStatus(
            @LoginMember AuthenticatedMember authenticatedMember,
            @PathVariable Long memberId,
            @Valid @RequestBody ChangeMemberStatusRequest request
    ) {
        AdminMemberResponse response = AdminMemberResponse.from(changeMemberStatusUseCase.changeStatus(
                new ChangeMemberStatusCommand(
                        authenticatedMember.memberId(),
                        authenticatedMember.role(),
                        memberId,
                        request.status()
                )
        ));
        return ResponseEntity.ok(response);
    }
}
