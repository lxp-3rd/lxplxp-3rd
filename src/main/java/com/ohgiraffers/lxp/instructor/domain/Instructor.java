package com.ohgiraffers.lxp.instructor.domain;

public class Instructor {

    private Long id;
    private Long memberId;
    private InstructorStatus status;

    private Instructor() {
    }

    public static Instructor create(Long memberId) {
        Instructor instructor = new Instructor();
        instructor.memberId = memberId;
        instructor.status = InstructorStatus.ACTIVE;
        return instructor;
    }

    public static Instructor reconstitute(Long id, Long memberId, InstructorStatus status) {
        Instructor instructor = new Instructor();
        instructor.id = id;
        instructor.memberId = memberId;
        instructor.status = status;
        return instructor;
    }

    public void withdraw() {
        if (this.status == InstructorStatus.DELETED) {
            throw new IllegalStateException("이미 탈퇴한 강사입니다.");
        }
        this.status = InstructorStatus.DELETED;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public InstructorStatus getStatus() {
        return status;
    }
}
