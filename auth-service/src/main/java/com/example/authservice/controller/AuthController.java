package com.example.authservice.controller;

import com.example.authservice.exception.AuthException;
import com.example.authservice.exception.RecourseNotFoundException;
import com.example.authservice.security.JwtRequest;
import com.example.authservice.security.JwtResponse;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) {
        log.info("Attempting login for user: {}", authRequest.getLogin());
        try {
            JwtResponse token = authService.login(authRequest);
            log.info("Login successful for user: {}", authRequest.getLogin());
            return ResponseEntity.ok(token);
        } catch (AuthException e) {
            log.warn("Login failed for user: {} due to incorrect login or password", authRequest.getLogin());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login or password");
        } catch (Exception e) {
            log.error("Unexpected error during login for user: {}", authRequest.getLogin(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }



}
