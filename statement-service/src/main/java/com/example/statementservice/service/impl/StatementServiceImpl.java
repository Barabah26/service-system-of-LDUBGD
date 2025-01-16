package com.example.statementservice.service.impl;

import com.example.statementservice.dto.StatementDto;
import com.example.statementservice.dto.StatementDtoRequest;
import com.example.statementservice.repository.UserRepository;
import com.ldubgd.components.dao.Statement;
import com.ldubgd.components.dao.StatementInfo;
import com.ldubgd.components.dao.User;
import com.ldubgd.components.dao.enums.StatementStatus;
import com.example.statementservice.exception.RecourseNotFoundException;
import com.example.statementservice.repository.StatementInfoRepository;
import com.example.statementservice.repository.StatementRepository;
import com.example.statementservice.service.StatementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class StatementServiceImpl implements StatementService {

    private final StatementInfoRepository statementInfoRepository;
    private final StatementRepository statementRepository;
    private final UserRepository userRepository;

    @Override
    public void createStatement(StatementDtoRequest statementDtoRequest) {
        User user = userRepository.findById(statementDtoRequest.getUserId()).orElseThrow(
                () -> new RecourseNotFoundException("User not found")
        );
        Statement statement = new Statement();
        statement.setFullName(statementDtoRequest.getFullName());
        statement.setYearBirthday(statementDtoRequest.getDateBirth());
        statement.setGroup(statementDtoRequest.getGroup());
        statement.setPhoneNumber(statementDtoRequest.getPhoneNumber());
        statement.setFaculty(statementDtoRequest.getFaculty());
        statement.setTypeOfStatement(statementDtoRequest.getTypeOfStatement());
        statement.setUser(user);

        statementRepository.save(statement);

        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setStatement(statement);
        statementInfo.setIsReady(false);
        statementInfo.setStatementStatus(StatementStatus.PENDING);

        statementInfoRepository.save(statementInfo);

    }

    @Override
    public List<StatementDto> getStatementsInfoWithStatusPending() {
        List<Object[]> results = statementInfoRepository.findStatementsInfoWithStatusPending();
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        return results.stream().map(this::mapToStatementDto).collect(Collectors.toList());
    }

    @Override
    public List<StatementDto> findStatementInfoByStatementFullName(String fullName) {
        List<Object[]> statements = statementInfoRepository.findStatementDtoByFullName(fullName);
        if (statements.isEmpty()) {
            return Collections.emptyList();
        }
        return statements.stream().map(this::mapToStatementDto).collect(Collectors.toList());
    }

    @Override
    public List<StatementDto> findStatementInfoByStatementFullNameAndStatus(String fullName, StatementStatus status) {
        List<Object[]> statements = statementInfoRepository.findStatementDtoByFullNameAndStatus(fullName, status.name());
        if (statements.isEmpty()) {
            return Collections.emptyList();
        }
        return statements.stream().map(this::mapToStatementDto).collect(Collectors.toList());
    }


    private StatementDto mapToStatementDto(Object[] result) {
        StatementDto dto = new StatementDto();
        dto.setId((Long) result[0]);
        dto.setFullName((String) result[1]);
        dto.setGroupName((String) result[2]);
        dto.setPhoneNumber((String) result[3]);
        dto.setTypeOfStatement((String) result[4]);
        dto.setFaculty((String) result[5]);
        dto.setYearBirthday((String) result[6]);

        Object statusObject = result[7];
        String statusString;

        if (statusObject instanceof Boolean) {
            statusString = (Boolean) statusObject ? "READY" : "PENDING";
        } else {
            statusString = (String) statusObject;
        }

        StatementStatus status = StatementStatus.valueOf(statusString);
        switch (status) {
            case IN_PROGRESS:
                dto.setStatus("В процесі");
                break;
            case READY:
                dto.setStatus("Готово");
                break;
            case PENDING:
                dto.setStatus("В очікуванні");
                break;
            default:
                dto.setStatus("Невідомий статус");
                break;
        }

        return dto;
    }



    @Override
    public void updateStatementStatus(Long statementId, StatementStatus status) {
        StatementInfo statement = statementInfoRepository.findById(statementId).orElseThrow(
                () -> new RecourseNotFoundException("Statement is not found with id: " + statementId)
        );
        statement.setStatementStatus(StatementStatus.valueOf(status.name()));

        statementInfoRepository.save(statement);
    }

    @Override
    public List<StatementDto> getStatementsInfoByStatusAndFaculty(StatementStatus status, String faculty) {
        List<Object[]> results = statementInfoRepository.findStatementInfoByStatusAndFaculty(status.name(), faculty);
        return results.stream().map(this::mapToStatementDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStatementIfReady(Long statementId, StatementStatus status, String faculty) {
        List<Object[]> results = statementInfoRepository.findStatementInfoByStatusAndFaculty(status.name(), faculty);
        if (!results.isEmpty()){
            statementInfoRepository.deleteStatementIfReady(status.name(), statementId, faculty);
        } else {
            throw new RecourseNotFoundException("Statements are not found");
        }
    }

    @Override
    public List<StatementDto> searchByName(String name) {
        return statementInfoRepository.findByNameContaining(name).stream().map(this::mapToStatementDto).collect(Collectors.toList());
    }
}
