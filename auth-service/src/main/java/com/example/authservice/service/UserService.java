package com.example.authservice.service;

import com.example.authservice.dto.UserDtoRequest;
import com.example.authservice.entity.User;
import lombok.NonNull;

import java.util.Optional;

public interface UserService {

    Optional<User> getByLogin(@NonNull String login);

    User registerUser(User user);
}
