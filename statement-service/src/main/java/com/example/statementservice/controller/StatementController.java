package com.example.statementservice.controller;

import com.example.statementservice.dto.StatementDtoRequest;
import com.example.statementservice.service.StatementService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<Void> createStatement(@RequestBody StatementDtoRequest statementRequestDto) {
        statementService.createStatement(statementRequestDto);
        return ResponseEntity.ok().build();
    }
}
