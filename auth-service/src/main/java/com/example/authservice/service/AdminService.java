package com.example.authservice.service;


import com.ldubgd.components.dao.Admin;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> findByLogin(String login);
}
