package com.example.authservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";

    private String accessToken;

    private String refreshToken;

    private String role;
}
