package com.example.authservice.service;

import com.example.authservice.entity.Admin;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> findByLogin(String login);
}
