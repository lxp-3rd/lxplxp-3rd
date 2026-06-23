package com.ohgiraffers.lxp.member.domain.model.entity;

import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;

public class Member {

    private final Long id;
    private final Email email;
    private final Nickname nickname;
    private final EncodedPassword password;
    private final MemberRole role;
    private final MemberStatus status;

    private Member(Long id, Email email, Nickname nickname, EncodedPassword password, MemberRole role, MemberStatus status) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public static Member signUp(Email email, Nickname nickname, EncodedPassword password) {
        return new Member(null, email, nickname, password, MemberRole.LEARNER, MemberStatus.ACTIVE);
    }

    public static Member restore(Long id, Email email, Nickname nickname, EncodedPassword password, MemberRole role, MemberStatus status) {
        return new Member(id, email, nickname, password, role, status);
    }

    public Member changePassword(EncodedPassword password) {
        return new Member(id, email, nickname, password, role, status);
    }

    public Member withdraw() {
        return new Member(id, email, nickname, password, role, MemberStatus.WITHDRAWN);
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public EncodedPassword getPassword() {
        return password;
    }

    public MemberRole getRole() {
        return role;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
