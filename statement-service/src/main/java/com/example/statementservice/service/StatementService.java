package com.example.statementservice.service;

import com.example.statementservice.dto.StatementDto;
import com.example.statementservice.dto.StatementDtoRequest;
import com.example.statementservice.entity.StatementInfo;
import com.example.statementservice.entity.enums.StatementStatus;
import com.example.statementservice.exception.RecourseNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface StatementService {
    void createStatement(StatementDtoRequest statementDtoRequest);
    List<StatementDto> getStatementsInfoWithStatusPending();
    void updateStatementStatus(Long statementId, StatementStatus status);
    List<StatementDto> getStatementsInfoByStatusAndFaculty(StatementStatus status, String faculty);
    void deleteStatementIfReady(Long statementId, StatementStatus status, String faculty);
    List<StatementDto> searchByName(String name);
}
