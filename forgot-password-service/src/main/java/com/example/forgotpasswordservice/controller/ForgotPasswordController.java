package com.example.forgotpasswordservice.controller;

import com.example.forgotpasswordservice.dto.ForgotPasswordDto;
import com.example.forgotpasswordservice.dto.ForgotPasswordRequestDto;
import com.example.forgotpasswordservice.dto.UpdateForgotPasswordDto;
import com.example.forgotpasswordservice.exception.RecourseNotFoundException;
import com.example.forgotpasswordservice.security.JwtTokenProvider;
import com.example.forgotpasswordservice.service.ForgotPasswordService;
import com.ldubgd.components.dao.enums.StatementStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/status")
    public ResponseEntity<List<ForgotPasswordDto>> getStatementsByStatus(@RequestParam(value = "status", required = false) StatementStatus status, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
            List<ForgotPasswordDto> forgotPasswords = new ArrayList<>();
            if (status != null) {
                forgotPasswords = forgotPasswordService.getForgotPasswordInfoByStatus(status);
            }
            if (forgotPasswords.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(forgotPasswords);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping("/statusAndFaculty")
    public ResponseEntity<List<ForgotPasswordDto>> getStatementsByStatusAndFaculty(@RequestParam(value = "status", required = false) StatementStatus status, @RequestParam(value = "faculty", required = false) String faculty, HttpServletRequest request) {

        Long currentUserId = getUserIdFromToken(request);

        if (currentUserId == null) {
            List<ForgotPasswordDto> forgotPasswordDtos = new ArrayList<>();

            if (status != null) {
                forgotPasswordDtos = forgotPasswordService.getForgotPasswordByStatusAndFaculty(status, faculty);
            }

            if (forgotPasswordDtos.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(forgotPasswordDtos);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PutMapping("{id}/in-progress")
    public ResponseEntity<String> markForgotPasswordInProgress(@PathVariable("id") Long statementId, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
            try {
                forgotPasswordService.updateForgotPasswordStatus(statementId, StatementStatus.IN_PROGRESS);
            } catch (RecourseNotFoundException e) {
                return ResponseEntity.status(404).body("ForgotPassword not found!");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to update ForgotPassword status!");
            }
            return ResponseEntity.ok("ForgotPassword marked as IN_PROGRESS successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PutMapping("{id}/ready")
    public ResponseEntity<String> markForgotPasswordReady(@PathVariable("id") Long statementId, HttpServletRequest request) { //
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
            try {
                forgotPasswordService.updateForgotPasswordStatus(statementId, StatementStatus.READY);
            } catch (RecourseNotFoundException e) {
                return ResponseEntity.status(404).body("ForgotPassword not found!");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to update ForgotPassword status!");
            }
            return ResponseEntity.ok("ForgotPassword marked as READY successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateForgotPassword(@PathVariable Long id, @RequestBody UpdateForgotPasswordDto updateDto) {

        boolean isUpdated = forgotPasswordService.updateForgotPassword(id, updateDto.getLogin(), updateDto.getPassword());

        if (isUpdated) {
            return ResponseEntity.ok("Логін і пароль успішно збережені! Змініть статус заявки на 'ГОТОВО'");
        } else {
            return ResponseEntity.badRequest().body("Nothing was updated, maybe fields are already set");
        }
    }

}
