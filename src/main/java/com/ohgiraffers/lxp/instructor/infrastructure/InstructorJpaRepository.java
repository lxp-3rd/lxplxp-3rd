package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.instructor.domain.InstructorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorJpaRepository extends JpaRepository<InstructorJpaEntity, Long> {

    boolean existsByIdAndStatusIn(Long id, List<InstructorStatus> statuses);

    boolean existsByMemberIdAndStatusIn(Long memberId, List<InstructorStatus> statuses);
}
