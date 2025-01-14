package com.example.authservice.controller;

import com.example.authservice.dto.UserDtoRequest;
import com.example.authservice.dto.UserDtoResponse;
import com.example.authservice.entity.User;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDtoRequest userDto) {
        try {
            User user = userMapper.toEntity(userDto);

            User registeredUser = userService.registerUser(user);

            UserDtoResponse response = new UserDtoResponse(
                    registeredUser.getUserId(),
                    registeredUser.getName(),
                    registeredUser.getEmail(),
                    registeredUser.getLogin(),
                    registeredUser.getRole(),
                    registeredUser.getFaculty(),
                    registeredUser.getSpecialty(),
                    registeredUser.getDegree(),
                    registeredUser.getGroup(),
                    registeredUser.getPhoneNumber(),
                    registeredUser.getDateBirth()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }    catch (IllegalArgumentException e) {
            log.error("Conflict during registration: User already exists. Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: " + e.getMessage());
        } catch (NullPointerException e) {
            log.error("Unexpected null pointer exception during registration. Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: Null pointer exception.");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

}
