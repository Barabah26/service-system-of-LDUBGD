package com.example.statementservice.service.impl;

import com.example.statementservice.dto.StatementDtoRequest;
import com.example.statementservice.entity.Statement;
import com.example.statementservice.entity.StatementInfo;
import com.example.statementservice.entity.enums.StatementStatus;
import com.example.statementservice.repository.StatementInfoRepository;
import com.example.statementservice.repository.StatementRepository;
import com.example.statementservice.service.StatementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class StatementServiceImpl implements StatementService {

    private final StatementInfoRepository statementInfoRepository;
    private final StatementRepository statementRepository;

    @Override
    public void createStatement(StatementDtoRequest statementDtoRequest) {
        Statement statement = new Statement();
        statement.setFullName(statementDtoRequest.getFullName());
        statement.setYearBirthday(statementDtoRequest.getYearBirthday());
        statement.setGroup(statementDtoRequest.getGroup());
        statement.setPhoneNumber(statementDtoRequest.getPhoneNumber());
        statement.setFaculty(statementDtoRequest.getFaculty());
        statement.setTypeOfStatement(statementDtoRequest.getTypeOfStatement());

        statementRepository.save(statement);

        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setStatement(statement);
        statementInfo.setIsReady(false);
        statementInfo.setStatementStatus(StatementStatus.PENDING);

        statementInfoRepository.save(statementInfo);

    }
}
