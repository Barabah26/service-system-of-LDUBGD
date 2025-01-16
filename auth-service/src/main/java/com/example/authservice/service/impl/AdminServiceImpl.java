package com.example.authservice.service.impl;

import com.example.authservice.repository.AdminRepository;
import com.example.authservice.service.AdminService;
import com.ldubgd.components.dao.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Optional<Admin> findByLogin(String login) {
        return adminRepository.findAdminByLogin(login);
    }
}
