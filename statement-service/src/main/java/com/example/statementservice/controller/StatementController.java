package com.example.statementservice.controller;

import com.example.statementservice.dto.StatementDto;
import com.example.statementservice.dto.StatementDtoRequest;
import com.example.statementservice.entity.enums.StatementStatus;
import com.example.statementservice.exception.RecourseNotFoundException;
import com.example.statementservice.security.JwtTokenProvider;
import com.example.statementservice.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<StatementDto>> getAllStatements() {
        List<StatementDto> statements = statementService.getStatementsInfoWithStatusPending();
        if (statements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(statements);
    }


    /**
     * Marks a statement as "IN_PROGRESS" based on its ID.
     *
     * @param statementId the ID of the statement to update.
     * @return ResponseEntity<String> - the response indicating the result of the update operation.
     */
    @PutMapping("{id}/in-progress") //
    public ResponseEntity<String> markStatementInProgress(@PathVariable("id") Long statementId) {
        try {
            statementService.updateStatementStatus(statementId, StatementStatus.IN_PROGRESS);
        } catch (RecourseNotFoundException e) {
            return ResponseEntity.status(404).body("Statement not found!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update statement status!");
        }
        return ResponseEntity.ok("Statement marked as IN_PROGRESS successfully!");
    }

    /**
     * Marks a statement as "READY" based on its ID.
     *
     * @param statementId the ID of the statement to update.
     * @return ResponseEntity<String> - the response indicating the result of the update operation.
     */
    @PutMapping("{id}/ready")
    public ResponseEntity<String> markStatementReady(@PathVariable("id") Long statementId) { //
        try {
            statementService.updateStatementStatus(statementId, StatementStatus.READY);
        } catch (RecourseNotFoundException e) {
            return ResponseEntity.status(404).body("Statement not found!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update statement status!");
        }
        return ResponseEntity.ok("Statement marked as READY successfully!");
    }

    @GetMapping("/statusAndFaculty") //
    public ResponseEntity<List<StatementDto>> getStatementsByStatusAndFaculty(
            @RequestParam(value = "status", required = false) StatementStatus status,
            @RequestParam(value = "faculty", required = false) String faculty) {


        List<StatementDto> statements = new ArrayList<>();

        if (status != null) {
            statements = statementService.getStatementsInfoByStatusAndFaculty(status, faculty);
        }

        if (statements.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(statements);
    }


    @GetMapping("/searchByName")
    public ResponseEntity<?> searchUsersByName(@RequestParam String name) {
        try {
            List<StatementDto> users = statementService.searchByName(name);
            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for users");
        }
    }

}
