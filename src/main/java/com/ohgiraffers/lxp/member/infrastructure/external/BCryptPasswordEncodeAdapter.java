package com.ohgiraffers.lxp.member.infrastructure.external;

import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordMatchPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncodeAdapter implements PasswordEncodePort, PasswordMatchPort {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
