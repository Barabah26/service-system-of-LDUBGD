package com.example.authservice.service.impl;

import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getByLogin(@NonNull String login) {
        return userRepository.findUsersByLogin(login);
    }

    @Override
    public User registerUser(User user) {
        if (userRepository.findUsersByLogin(user.getLogin()).isPresent()) {
            throw new IllegalArgumentException("User with login '" + user.getLogin() + "' already exists.");
        }
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setLogin(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());

        return userRepository.save(user);
    }

}
