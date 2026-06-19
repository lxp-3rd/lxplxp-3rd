package com.ohgiraffers.lxp.member.infrastructure.external;

import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncodeAdapter implements PasswordEncodePort {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordEncodeAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
