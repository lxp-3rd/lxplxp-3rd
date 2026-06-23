package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.MemberProfileResult;
import com.ohgiraffers.lxp.member.application.port.command.UpdateMyProfileCommand;
import com.ohgiraffers.lxp.member.application.port.in.GetMyProfileUseCase;
import com.ohgiraffers.lxp.member.application.port.in.UpdateMyProfileUseCase;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberProfileService implements GetMyProfileUseCase, UpdateMyProfileUseCase {

    private final MemberRepositoryPort memberRepositoryPort;

    public MemberProfileService(MemberRepositoryPort memberRepositoryPort) {
        this.memberRepositoryPort = memberRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberProfileResult getMyProfile(Long memberId) {
        return MemberProfileResult.from(getMember(memberId));
    }

    @Override
    @Transactional
    public MemberProfileResult updateMyProfile(UpdateMyProfileCommand command) {
        Member member = getMember(command.memberId());
        Nickname nickname = new Nickname(command.nickname());

        if (!member.getNickname().equals(nickname) && memberRepositoryPort.existsByNickname(nickname)) {
            throw new BusinessException(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
        }

        Member updatedMember = Member.restore(
                member.getId(),
                member.getEmail(),
                nickname,
                member.getPassword(),
                member.getRole(),
                member.getStatus()
        );

        return MemberProfileResult.from(memberRepositoryPort.save(updatedMember));
    }

    private Member getMember(Long memberId) {
        return memberRepositoryPort.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
