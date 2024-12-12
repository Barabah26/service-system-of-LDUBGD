package com.example.statementservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDtoRequest {

    @NotEmpty(message = "Full name must not be empty")
    @Pattern(regexp = "^[А-ЯІЇЄҐ][а-яіїєґ]+ [А-ЯІЇЄҐ][а-яіїєґ]+ [А-ЯІЇЄҐ][а-яіїєґ]+$", message = "Full name must be in 'Surname FirstName Patronymic' format")
    private String fullName;

    @NotEmpty(message = "Year of birth must not be empty")
    @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$", message = "Year of birth must be in 'dd-MM-yyyy' format")
    private String yearBirthday;

    @NotEmpty(message = "Group must not be empty")
    @Pattern(regexp = "^[А-ЯІЇЄҐ][A-Za-z0-9]+$", message = "Group must not contain special characters")
    private String group;

    @NotEmpty(message = "Phone number must not be empty")
    @Pattern(regexp = "^\\+380[0-9]{9}$", message = "Phone number must be in '+380xxxxxxxxx' format")
    private String phoneNumber;

    @NotEmpty(message = "Faculty must not be empty")
    private String faculty;

    @NotEmpty(message = "Type of statement must not be empty")
    private String typeOfStatement;
}
