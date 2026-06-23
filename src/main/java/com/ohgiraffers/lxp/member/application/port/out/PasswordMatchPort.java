package com.ohgiraffers.lxp.member.application.port.out;

public interface PasswordMatchPort {

    boolean matches(String rawPassword, String encodedPassword);
}
