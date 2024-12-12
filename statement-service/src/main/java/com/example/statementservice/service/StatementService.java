package com.example.statementservice.service;

import com.example.statementservice.dto.StatementDtoRequest;

public interface StatementService {
    void createStatement(StatementDtoRequest statementDtoRequest);
}
