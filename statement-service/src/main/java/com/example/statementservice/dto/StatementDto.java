package com.example.statementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for transferring statement data.
 * Used for carrying statement information between layers of the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {

    /**
     * The unique identifier for the statement.
     */
    private Long id;

    /**
     * The full name of the individual associated with the statement.
     */
    private String fullName;

    /**
     * The group name associated with the statement.
     */
    private String groupName;

    /**
     * The phone number of the individual associated with the statement.
     */
    private String phoneNumber;

    /**
     * The type of the statement (e.g., application, request).
     */
    private String typeOfStatement;

    /**
     * The faculty to which the statement is related.
     */
    private String faculty;

    /**
     * The student date of birth
     */
    private String yearBirthday;

    private String  status;

}
