package com.example.authservice.controller;

import com.example.authservice.security.JwtRequest;
import com.example.authservice.security.JwtResponse;
import com.example.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        log.info("Attempting login for user: {}", authRequest.getLogin());
        try {
            final JwtResponse token = authService.login(authRequest);
            log.info("Login successful for user: {}", authRequest.getLogin());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error("Login failed for user: {}", authRequest.getLogin(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

}
