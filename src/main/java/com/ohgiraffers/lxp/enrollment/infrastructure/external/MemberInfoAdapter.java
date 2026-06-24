package com.ohgiraffers.lxp.enrollment.infrastructure.external;

import com.ohgiraffers.lxp.enrollment.application.dto.MemberInfo;
import com.ohgiraffers.lxp.enrollment.application.dto.Role;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadMemberInfoPort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberInfoAdapter implements LoadMemberInfoPort {

    private final MemberJpaRepository memberJpaRepository;

    public MemberInfoAdapter(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public MemberInfo load(Long memberId) {
        var entity = memberJpaRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        Role role = Role.valueOf(entity.getRole().name());
        boolean suspended = entity.getStatus() == MemberStatus.SUSPENDED;
        return new MemberInfo(memberId, role, suspended);
    }
}
