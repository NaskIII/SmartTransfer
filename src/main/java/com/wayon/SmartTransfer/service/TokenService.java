package com.wayon.SmartTransfer.service;

import com.wayon.SmartTransfer.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${smart-transfer.jwt.expiration}")
    private String expiration;

    @Value("${smart-transfer.jwt.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("SmartTransfer API")
                .setSubject(((User) auth.getPrincipal()).getUserId())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}
