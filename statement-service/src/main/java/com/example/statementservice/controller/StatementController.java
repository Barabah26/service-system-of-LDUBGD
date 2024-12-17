package com.example.statementservice.controller;

import com.example.statementservice.dto.StatementDtoRequest;
import com.example.statementservice.security.JwtTokenProvider;
import com.example.statementservice.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementController {

    private final StatementService statementService;
    private final JwtTokenProvider jwtTokenProvider;

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return jwtTokenProvider.getId(token);
        }
        return null;
    }

    @Operation(summary = "Create a new statement", description = "This endpoint allows users to create a new statement.")
    @ApiResponse(responseCode = "200", description = "Statement created successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PostMapping("/createStatement")
    public ResponseEntity<String> createStatement(@RequestBody StatementDtoRequest statementRequestDto, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId != null) {
            statementService.createStatement(statementRequestDto);
            return ResponseEntity.ok("Statement created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

}
