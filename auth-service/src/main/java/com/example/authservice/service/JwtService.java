package com.example.authservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authservice.exception.AuthException;
import com.example.authservice.security.JwtProvider;
import com.example.authservice.service.impl.UserServiceImpl;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Getter
public class JwtService {

    private final UserServiceImpl userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final Map<String, List<String>> accessStorage = new HashMap<>();
    private final JwtProvider jwtProvider;


    public boolean validateAccessToken(@NonNull String accessToken) {
        return jwtProvider.validateAccessToken(accessToken);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return jwtProvider.validateRefreshToken(refreshToken);
    }

    public String decodeToken(@NonNull String token) {
        if (jwtProvider.validateAccessToken(token)) {
            String[] chunks = token.split("\\.");

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String header = new String(decoder.decode(chunks[0]));
            String payload = new String(decoder.decode(chunks[1]));

            return header + "\n" + payload;
        } else {
            throw new AuthException("JWT token is invalid");
        }
    }

    public boolean isJwtExpired(@NonNull String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }
}
