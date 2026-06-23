package com.ohgiraffers.lxp.auth.infrastructure.token;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenValidateAdapter implements TokenValidatePort {

    private final SecretKey secretKey;

    public JwtTokenValidateAdapter(@Value("${lxp.auth.jwt.secret}") String secret) {
        this.secretKey = JwtSecretKeyFactory.create(secret);
    }

    @Override
    public AuthenticatedMember validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return toAuthenticatedMember(claims);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        } catch (BusinessException e) {
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }
    }

    private AuthenticatedMember toAuthenticatedMember(Claims claims) {
        String tokenType = claims.get("type", String.class);
        if (!JwtTokenType.ACCESS.equals(tokenType)) {
            throw new BusinessException(ErrorCode.TOKEN_TYPE_MISMATCH);
        }

        Object memberIdClaim = claims.get("memberId");
        if (!(memberIdClaim instanceof Number memberIdNumber)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        String roleName = claims.get("role", String.class);
        if (roleName == null || roleName.isBlank()) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        return new AuthenticatedMember(
                memberIdNumber.longValue(),
                MemberRole.valueOf(roleName)
        );
    }
}
