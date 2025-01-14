package com.example.authservice.controller;

import com.example.authservice.dto.UserProfileDtoResponse;
import com.example.authservice.exception.AuthException;
import com.example.authservice.security.JwtRequest;
import com.example.authservice.security.JwtResponse;
import com.example.authservice.service.impl.AuthService;
import com.example.authservice.service.impl.JwtService;
import com.example.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/student/login")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest authRequest) {
        log.info("Attempting login for user: {}", authRequest.getLogin());
        try {
            JwtResponse token = authService.loginUser(authRequest);
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

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody JwtRequest authRequest) {
        log.info("Attempting login for admin: {}", authRequest.getLogin());
        try {
            log.info("Received login: {}, password: {}", authRequest.getLogin(), authRequest.getPassword());
            JwtResponse token = authService.loginAdmin(authRequest);
            log.info("Admin login successful for admin: {}", authRequest.getLogin());
            return ResponseEntity.ok(token);
        } catch (AuthException e) {
            log.warn("Admin login failed for admin: {} due to incorrect login or password", authRequest.getLogin());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login or password");
        } catch (Exception e) {
            log.error("Unexpected error during admin login for admin: {}", authRequest.getLogin(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/profile")
    public ResponseEntity<UserProfileDtoResponse> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        try {
            Long userId = jwtService.extractUserIdFromToken(token);

            UserProfileDtoResponse userProfileDtoResponse = userService.getUserProfile(userId);

            if (userProfileDtoResponse != null) {
                return ResponseEntity.ok(userProfileDtoResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
