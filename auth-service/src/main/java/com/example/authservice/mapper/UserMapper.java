package com.example.authservice.mapper;

import com.example.authservice.dto.UserDtoRequest;
import com.ldubgd.components.dao.User;
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

}
