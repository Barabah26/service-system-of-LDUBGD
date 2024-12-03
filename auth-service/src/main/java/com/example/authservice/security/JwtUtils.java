package com.example.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwt.secret.access}")
    private String accessTokenSecret;


    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token, accessTokenSecret).getSubject();
    }


    public String getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token, accessTokenSecret);
        return claims.get("roles", String.class);
    }


    private Claims getAllClaimsFromToken(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getAllClaimsFromToken(token, accessTokenSecret).getExpiration();
        return expiration.before(new Date());
    }
}
