package com.example.statementservice.service;

import com.example.statementservice.dto.StatementDto;
import com.example.statementservice.dto.StatementDtoRequest;
import com.ldubgd.components.dao.enums.StatementStatus;

import java.util.List;

public interface StatementService {
    void createStatement(StatementDtoRequest statementDtoRequest);
    List<StatementDto> getStatementsInfoWithStatusPending();
    void updateStatementStatus(Long statementId, StatementStatus status);
    List<StatementDto> getStatementsInfoByStatusAndFaculty(StatementStatus status, String faculty);
    void deleteStatementIfReady(Long statementId, StatementStatus status, String faculty);
    List<StatementDto> searchByName(String name);
    List<StatementDto> findStatementInfoByStatementFullName(String fullName);
    List<StatementDto> findStatementInfoByStatementFullNameAndStatus(String fullName, StatementStatus status);
}
