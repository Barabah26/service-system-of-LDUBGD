package com.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDtoResponse {
    private String fullName;
    private String faculty;
    private String specialty;
    private String degree;
    private String group;
    private String phoneNumber;
    private String dateBirth;
}
