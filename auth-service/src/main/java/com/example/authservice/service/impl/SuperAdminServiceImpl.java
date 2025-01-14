package com.example.authservice.service.impl;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.UpdateAdminDto;
import com.example.authservice.entity.Admin;
import com.example.authservice.exception.RecourseNotFoundException;
import com.example.authservice.repository.AdminRepository;
import com.example.authservice.service.SuperAdminService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Override
    public Optional<Admin> getByLogin(@NonNull String login) {
        return adminRepository.findAdminByLogin(login);
    }

    @Override
    public AdminDto registerAdmin(AdminDto adminDto) {
        if (adminRepository.findAdminByLogin(adminDto.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Admin already exists");
        }

        Admin admin = new Admin();
        admin.setName(adminDto.getName());
        admin.setLogin(adminDto.getLogin());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        admin.setRole(adminDto.getRole());

        Admin saved = adminRepository.save(admin);

        return new AdminDto(saved.getName(), saved.getLogin(), saved.getPassword(), saved.getRole());
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void deleteAdminByLogin(String login) {
        Admin admin = adminRepository.findAdminByLogin(login)
                .orElseThrow(() -> new RecourseNotFoundException("Admin not found with login: " + login));
        adminRepository.delete(admin);
    }

    @Override
    public UpdateAdminDto updateAdminPassword(String login, UpdateAdminDto adminDto) {
        Optional<Admin> optionalAdmin = adminRepository.findAdminByLogin(login);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setPassword(passwordEncoder.encode(adminDto.getNewPassword()));
            adminRepository.updateUserByUsername(login, passwordEncoder.encode(adminDto.getNewPassword()));
            return adminDto;
        } else {
            throw new RecourseNotFoundException("Admin not found");
        }
    }
}
