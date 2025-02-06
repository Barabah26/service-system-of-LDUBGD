package com.example.forgotpasswordservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDto {
    private Long id;
    private String typeOfForgotPassword;
    private Long userId;
    private String status;
    private String fullName;
    private String groupName;
    private String faculty;
}

