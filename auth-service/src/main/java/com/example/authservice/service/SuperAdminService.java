package com.example.authservice.service;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.UpdateAdminDto;
import com.ldubgd.components.dao.Admin;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;


public interface SuperAdminService {
    Optional<Admin> getByLogin(@NonNull String login);

    AdminDto registerAdmin(AdminDto adminDto);

    List<Admin> getAllAdmins();

    void deleteAdminByLogin(String login);

    UpdateAdminDto updateAdminPassword(String login, UpdateAdminDto userDto);
}
