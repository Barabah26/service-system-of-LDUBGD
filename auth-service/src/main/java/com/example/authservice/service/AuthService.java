package com.example.authservice.service;

import com.example.authservice.entity.User;
import com.example.authservice.exception.AuthException;
import com.example.authservice.security.JwtProvider;
import com.example.authservice.security.JwtRequest;
import com.example.authservice.security.JwtResponse;
import com.example.authservice.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceImpl userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        if (authRequest.getLogin() == null) {
            throw new AuthException("Username is null");
        }
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User not found"));

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            jwtService.getRefreshStorage().put(user.getLogin(), refreshToken);

            List<String> accessTokens = jwtService.getAccessStorage().computeIfAbsent(user.getLogin(), k -> new ArrayList<>());
            accessTokens.add(accessToken);
            jwtService.getAccessStorage().put(user.getLogin(), accessTokens);

            String role = user.getRole();

            return new JwtResponse(accessToken, refreshToken, role);
        } else {
            throw new AuthException("Password is incorrect");
        }
    }


    public boolean revokeToken(@NonNull String accessToken) {
        if (jwtProvider.validateAccessToken(accessToken)) {
            final Claims claims = jwtProvider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            List<String> tokens = jwtService.getAccessStorage().get(login);
            if (tokens != null) {
                tokens.remove(accessToken);
                if (tokens.isEmpty()) {
                    jwtService.getAccessStorage().remove(login);
                } else {
                    jwtService.getAccessStorage().put(login, tokens);
                }
                return true;
            }
        }
        return false;
    }
}
