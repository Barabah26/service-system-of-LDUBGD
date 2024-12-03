package com.example.authservice.security;

import com.example.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";

    private String accessToken;

    private String refreshToken;

    private Role role;
}
