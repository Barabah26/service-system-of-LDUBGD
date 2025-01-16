package com.example.statementservice.mapper;

import com.example.statementservice.dto.StatementDtoRequest;
import com.ldubgd.components.dao.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatementMapper {
    public Statement toEntity(StatementDtoRequest statementDtoRequest) {
        if (statementDtoRequest == null) {
            return null;
        }

        Statement statement = new Statement();
        statement.setFullName(statementDtoRequest.getFullName());
        statement.setYearBirthday(statementDtoRequest.getDateBirth());
        statement.setGroup(statementDtoRequest.getGroup());
        statement.setPhoneNumber(statementDtoRequest.getPhoneNumber());
        statement.setFaculty(statementDtoRequest.getFaculty());
        statement.setTypeOfStatement(statementDtoRequest.getTypeOfStatement());


        return statement;
    }


    public StatementDtoRequest toDto(Statement statement) {
        if (statement == null) {
            return null;
        }

        StatementDtoRequest statementDtoRequest = new StatementDtoRequest();
        statementDtoRequest.setFullName(statement.getFullName());
        statementDtoRequest.setDateBirth(statement.getYearBirthday());
        statementDtoRequest.setGroup(statement.getGroup());
        statementDtoRequest.setPhoneNumber(statement.getPhoneNumber());
        statementDtoRequest.setFaculty(statement.getFaculty());
        statementDtoRequest.setTypeOfStatement(statement.getTypeOfStatement());

        return statementDtoRequest;
    }
}
