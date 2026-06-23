package com.ohgiraffers.lxp.member.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.application.port.out.AdminMemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class MemberPersistenceAdapter implements MemberRepositoryPort, AdminMemberRepositoryPort {

    private static final String NICKNAME_UNIQUE_CONSTRAINT = "uk_members_nickname";

    private final MemberJpaRepository memberJpaRepository;

    public MemberPersistenceAdapter(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public boolean existsByEmail(Email email) {
        return memberJpaRepository.existsByEmail(email.value());
    }

    @Override
    public boolean existsByNickname(Nickname nickname) {
        return memberJpaRepository.existsByNickname(nickname.value());
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberJpaRepository.findByIdAndDeletedAtIsNull(memberId)
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Optional<Member> findByEmail(Email email) {
        return memberJpaRepository.findByEmail(email.value())
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        try {
            return memberJpaRepository.saveAndFlush(MemberJpaEntity.from(member)).toDomain();
        } catch (DataIntegrityViolationException e) {
            if (isNicknameUniqueConstraintViolation(e)) {
                throw new BusinessException(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
            }
            throw e;
        }
    }

    @Override
    public List<AdminMemberResult> findAll() {
        return memberJpaRepository.findAllByDeletedAtIsNullOrderByIdDesc().stream()
                .map(this::toAdminMemberResult)
                .toList();
    }

    @Override
    public Optional<AdminMemberResult> findById(Long memberId) {
        return memberJpaRepository.findByIdAndDeletedAtIsNull(memberId)
                .map(this::toAdminMemberResult);
    }

    @Override
    public Optional<AdminMemberResult> changeStatus(Long memberId, MemberStatus status) {
        return memberJpaRepository.findByIdAndDeletedAtIsNull(memberId)
                .map(member -> {
                    member.changeStatus(status);
                    return toAdminMemberResult(member);
                });
    }

    private AdminMemberResult toAdminMemberResult(MemberJpaEntity member) {
        return new AdminMemberResult(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getRole(),
                member.getStatus(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    private boolean isNicknameUniqueConstraintViolation(DataIntegrityViolationException e) {
        Throwable cause = e;
        while (cause != null) {
            if (cause instanceof ConstraintViolationException constraintViolationException) {
                String constraintName = constraintViolationException.getConstraintName();
                if (constraintName != null && constraintName.equalsIgnoreCase(NICKNAME_UNIQUE_CONSTRAINT)) {
                    return true;
                }
            }
            String message = cause.getMessage();
            if (message != null) {
                String lowerMessage = message.toLowerCase(Locale.ROOT);
                if (lowerMessage.contains(NICKNAME_UNIQUE_CONSTRAINT)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
