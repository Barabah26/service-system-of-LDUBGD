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

    @NotEmpty(message = "Type of forgot password must not be empty")
    private String typeOfForgotPassword;

    @NotEmpty(message = "User ID must not be empty")
    private Long userId;
}
