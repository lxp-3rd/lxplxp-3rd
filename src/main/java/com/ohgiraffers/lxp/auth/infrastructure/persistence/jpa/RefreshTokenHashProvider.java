package com.ohgiraffers.lxp.auth.infrastructure.persistence.jpa;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class RefreshTokenHashProvider {

    private RefreshTokenHashProvider() {
    }

    static String hash(String refreshToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder(hashedBytes.length * 2);
            for (byte hashedByte : hashedBytes) {
                builder.append(String.format("%02x", hashedByte));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }
}
