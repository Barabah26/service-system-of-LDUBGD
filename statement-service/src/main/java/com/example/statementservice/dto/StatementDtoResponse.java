package com.example.statementservice.dto;

import com.example.statementservice.entity.StatementInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDtoResponse {

    private Long id;
    private String fullName;
    private String yearBirthday;
    private String group;
    private String phoneNumber;
    private String faculty;
    private String typeOfStatement;
    private StatementInfo statementInfo;
}
