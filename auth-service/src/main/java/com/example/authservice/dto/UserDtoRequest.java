package com.example.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 36, message = "Name must be at most 36 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁіІїЇєЄ\\s'-]+$",
            message = "Name can only contain letters, spaces, hyphens, and apostrophes")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Size(max = 128, message = "Email must be at most 128 characters long")
    @Pattern(
            regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
            message = "Email must contain '@' and '.' in the correct format"
    )
    private String email;

    @NotBlank(message = "Login cannot be blank")
    @Size(min = 4, max = 20, message = "Login must be between 4 and 128 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
            message = "Login can only contain letters, numbers, dots, underscores, and hyphens")
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,128}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
    )
    private String password;

    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "^(STUDENT|EMPLOYEE)$",
            message = "Role must be one of the following: STUDENT, EMPLOYEE")
    private String role;
}
