package com.example.userprofileservice.controller;

import com.example.userprofileservice.dto.UserProfileDtoRequest;
import com.example.userprofileservice.dto.UserProfileDtoResponse;
import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.mapper.UserProfileMapper;
import com.example.userprofileservice.security.JwtTokenProvider;
import com.example.userprofileservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/userprofiles")
@AllArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserProfileMapper userProfileMapper;

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return jwtTokenProvider.getId(token);
        }
        return null;
    }

    @Operation(summary = "Отримати профіль користувача за ID", description = "Отримує профіль користувача за його ID з перевіркою токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профіль знайдено"),
            @ApiResponse(responseCode = "404", description = "Профіль не знайдено"),
            @ApiResponse(responseCode = "401", description = "Неавторизований доступ")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@Parameter(description = "ID користувача") @PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId != null) {
            Optional<UserProfile> userProfile = userProfileService.getProfileById(id);
            return userProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Оновити профіль користувача", description = "Оновлює дані профілю користувача за його ID з перевіркою токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профіль оновлено"),
            @ApiResponse(responseCode = "404", description = "Профіль не знайдено"),
            @ApiResponse(responseCode = "401", description = "Неавторизований доступ")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDtoResponse> updateProfile(@Parameter(description = "ID користувача") @PathVariable Long id, @RequestBody UserProfileDtoRequest userProfileData, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId != null) {
            UserProfileDtoResponse updatedProfile = userProfileService.updateProfile(id, userProfileData);
            return updatedProfile != null ? ResponseEntity.ok(updatedProfile) : ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
