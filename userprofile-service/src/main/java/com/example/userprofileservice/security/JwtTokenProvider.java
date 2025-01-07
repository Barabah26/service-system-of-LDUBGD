package com.example.userprofileservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    public boolean isValid(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecret())
                    .build()
                    .parseClaimsJws(token);
            System.out.println("Our date" + new Date());
            System.out.println("Data of token" + claims.getBody()
                    .getExpiration());
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Long getId(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    private String getUsername(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Authentication getAuthentication(final String token) {
        String username = getUsername(token);
        return new UsernamePasswordAuthenticationToken(
                username,
                "",
                new ArrayList<>()
        );
    }
}
