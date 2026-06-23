package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberCommand;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberDetailCommand;
import com.ohgiraffers.lxp.member.application.port.command.ChangeMemberStatusCommand;
import com.ohgiraffers.lxp.member.application.port.in.ChangeMemberStatusUseCase;
import com.ohgiraffers.lxp.member.application.port.in.GetAdminMemberDetailUseCase;
import com.ohgiraffers.lxp.member.application.port.in.GetAdminMemberListUseCase;
import com.ohgiraffers.lxp.member.application.port.out.AdminMemberRepositoryPort;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminMemberService implements GetAdminMemberListUseCase, GetAdminMemberDetailUseCase, ChangeMemberStatusUseCase {

    private final AdminMemberRepositoryPort adminMemberRepositoryPort;

    public AdminMemberService(AdminMemberRepositoryPort adminMemberRepositoryPort) {
        this.adminMemberRepositoryPort = adminMemberRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminMemberResult> getMembers(AdminMemberCommand command) {
        validateAdmin(command.requesterRole());
        return adminMemberRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminMemberResult getMember(AdminMemberDetailCommand command) {
        validateAdmin(command.requesterRole());
        return adminMemberRepositoryPort.findById(command.memberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public AdminMemberResult changeStatus(ChangeMemberStatusCommand command) {
        validateAdmin(command.requesterRole());
        validateStatus(command);
        return adminMemberRepositoryPort.changeStatus(command.memberId(), command.status())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateAdmin(MemberRole requesterRole) {
        if (requesterRole != MemberRole.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }

    private void validateStatus(ChangeMemberStatusCommand command) {
        if (command.status() == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }
}
