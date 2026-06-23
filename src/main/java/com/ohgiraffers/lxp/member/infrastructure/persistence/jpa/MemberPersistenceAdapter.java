package com.ohgiraffers.lxp.member.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberPersistenceAdapter implements MemberRepositoryPort {

    private final MemberJpaRepository memberJpaRepository;

    public MemberPersistenceAdapter(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public boolean existsByEmail(Email email) {
        return memberJpaRepository.existsByEmail(email.value());
    }

    @Override
    public Optional<Member> findByEmail(Email email) {
        return memberJpaRepository.findByEmail(email.value())
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberJpaEntity.from(member)).toDomain();
    }
}
