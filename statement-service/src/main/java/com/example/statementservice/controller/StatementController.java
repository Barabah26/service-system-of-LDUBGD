package com.example.statementservice.controller;

import com.example.statementservice.dto.StatementDto;
import com.example.statementservice.dto.StatementDtoRequest;
import com.ldubgd.components.dao.enums.StatementStatus;
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
    public ResponseEntity<List<StatementDto>> getAllStatements(HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
            List<StatementDto> statements = statementService.getStatementsInfoWithStatusPending();
            if (statements.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(statements);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/findByFullName")
    public ResponseEntity<List<StatementDto>> findByFullName(@RequestParam String fullName, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId != null) {
            List<StatementDto> statements = statementService.findStatementInfoByStatementFullName(fullName);
            if (statements.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(statements);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping("/findByFullNameAndStatus")
    public ResponseEntity<List<StatementDto>> findByFullNameAndStatus( @RequestParam(value = "status") StatementStatus status,
                                                                       @RequestParam(value = "fullName") String fullName, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId != null) {
            List<StatementDto> statements = statementService.findStatementInfoByStatementFullNameAndStatus(fullName, status);
            if (statements.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(statements);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("{id}/in-progress")
    public ResponseEntity<String> markStatementInProgress(@PathVariable("id") Long statementId, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
            try {
                statementService.updateStatementStatus(statementId, StatementStatus.IN_PROGRESS);
            } catch (RecourseNotFoundException e) {
                return ResponseEntity.status(404).body("Statement not found!");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to update statement status!");
            }
            return ResponseEntity.ok("Statement marked as IN_PROGRESS successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PutMapping("{id}/ready")
    public ResponseEntity<String> markStatementReady(@PathVariable("id") Long statementId, HttpServletRequest request) { //
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
            try {
                statementService.updateStatementStatus(statementId, StatementStatus.READY);
            } catch (RecourseNotFoundException e) {
                return ResponseEntity.status(404).body("Statement not found!");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to update statement status!");
            }
            return ResponseEntity.ok("Statement marked as READY successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping("/statusAndFaculty") //
    public ResponseEntity<List<StatementDto>> getStatementsByStatusAndFaculty(
            @RequestParam(value = "status", required = false) StatementStatus status,
            @RequestParam(value = "faculty", required = false) String faculty,
            HttpServletRequest request) {

        Long currentUserId = getUserIdFromToken(request);

        if (currentUserId == null) {
            List<StatementDto> statements = new ArrayList<>();

            if (status != null) {
                statements = statementService.getStatementsInfoByStatusAndFaculty(status, faculty);
            }

            if (statements.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(statements);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


    @GetMapping("/searchByName")
    public ResponseEntity<?> searchUsersByName(@RequestParam String name, HttpServletRequest request) {
        Long currentUserId = getUserIdFromToken(request);
        if (currentUserId == null) {
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
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
