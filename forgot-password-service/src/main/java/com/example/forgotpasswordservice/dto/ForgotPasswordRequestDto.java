package com.example.forgotpasswordservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequestDto {

    @NotEmpty(message = "Full name must not be empty")
    @Pattern(regexp = "^[А-ЯІЇЄҐ][а-яіїєґ]+ [А-ЯІЇЄҐ][а-яіїєґ]+ [А-ЯІЇЄҐ][а-яіїєґ]+$", message = "Full name must be in 'Surname FirstName Patronymic' format")
    private String fullName;

    @NotEmpty(message = "Group must not be empty")
    @Pattern(regexp = "^[А-ЯІЇЄҐ][A-Za-z0-9]+$", message = "Group must not contain special characters")
    private String group;

    @NotEmpty(message = "Faculty must not be empty")
    private String faculty;

    @NotEmpty(message = "Type of forgot password must not be empty")
    private String typeOfForgotPassword;

    @NotEmpty(message = "User ID must not be empty")
    private Long userId;
}
