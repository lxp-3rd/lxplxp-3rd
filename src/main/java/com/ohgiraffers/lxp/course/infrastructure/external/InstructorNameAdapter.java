package com.ohgiraffers.lxp.course.infrastructure.external;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaEntity;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaRepository;

/**
 * member 컨텍스트에서 강사 표시 이름(nickname)을 batch로 조회해 Course 측 out-port를 구현한다.
 */
@Component
public class InstructorNameAdapter implements LoadInstructorNamePort {

    private final MemberJpaRepository memberJpaRepository;

    public InstructorNameAdapter(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Map<Long, String> findNamesByInstructorIds(List<Long> instructorIds) {
        if (instructorIds.isEmpty()) {
            return Map.of();
        }
        return memberJpaRepository
                .findByIdInAndRoleAndDeletedAtIsNull(instructorIds, MemberRole.INSTRUCTOR).stream()
                .collect(Collectors.toMap(MemberJpaEntity::getId, MemberJpaEntity::getNickname));
    }
}
