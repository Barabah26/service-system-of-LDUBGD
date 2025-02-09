package com.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    private Long userId;
    private String name;
    private String email;
    private String login;
    private String role;
    private String faculty;
    private String specialty;
    private String degree;
    private String group;
    private String phoneNumber;
    private String dateBirth;
}

