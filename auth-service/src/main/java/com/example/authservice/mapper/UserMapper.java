package com.example.authservice.mapper;

import com.example.authservice.dto.UserDtoRequest;
import com.example.authservice.entity.User;
import com.example.authservice.exception.RecourseNotFoundException;
import com.example.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {
    public User toEntity(UserDtoRequest userDtoRequest) {
        if (userDtoRequest == null) {
            return null;
        }

        User user = new User();
        user.setName(userDtoRequest.getName());
        user.setEmail(userDtoRequest.getEmail());
        user.setLogin(userDtoRequest.getLogin());
        user.setPassword(userDtoRequest.getPassword());
        user.setRole(userDtoRequest.getRole());
        user.setFaculty(userDtoRequest.getFaculty());
        user.setSpecialty(userDtoRequest.getSpecialty());
        user.setDegree(userDtoRequest.getDegree());
        user.setGroup(userDtoRequest.getGroup());
        user.setPhoneNumber(userDtoRequest.getPhoneNumber());
        user.setDateBirth(userDtoRequest.getDateBirth());

        return user;
    }


    public UserDtoRequest toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDtoRequest userDtoRequest = new UserDtoRequest();
        userDtoRequest.setName(user.getName());
        userDtoRequest.setEmail(user.getEmail());
        userDtoRequest.setLogin(user.getLogin());
        userDtoRequest.setPassword(user.getPassword());

        return userDtoRequest;
    }
}
