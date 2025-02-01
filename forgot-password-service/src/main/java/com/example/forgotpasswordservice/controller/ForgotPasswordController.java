package com.example.forgotpasswordservice.controller;

import com.example.forgotpasswordservice.dto.ForgotPasswordRequestDto;
import com.example.forgotpasswordservice.security.JwtTokenProvider;
import com.example.forgotpasswordservice.service.ForgotPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;
    private final JwtTokenProvider jwtTokenProvider;

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return jwtTokenProvider.getId(token);
        }
        return null;
    }

    @PostMapping("/createForgotPasswordStatement")
    @Operation(
            summary = "Create a forgot password statement",
            description = "This endpoint creates a new statement for a forgot password request.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Forgot Password statement created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    public ResponseEntity<String> createForgotPasswordStatement(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId != null) {
            forgotPasswordService.createForgotPasswordStatement(forgotPasswordRequestDto);
            return ResponseEntity.ok("Forgot Password statement created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
