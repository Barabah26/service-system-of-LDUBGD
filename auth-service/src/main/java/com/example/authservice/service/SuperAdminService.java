package com.example.authservice.service;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.UpdateAdminDto;
import com.example.authservice.dto.UpdateUserDto;
import com.ldubgd.components.dao.Admin;
import com.ldubgd.components.dao.User;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;


public interface SuperAdminService {
    Optional<Admin> getByLogin(@NonNull String login);

    AdminDto registerAdmin(AdminDto adminDto);

    List<Admin> getAllAdmins();

    List<User> getAllUsers();

    void deleteAdminByLogin(String login);
    void deleteUserByLogin(String login);

    UpdateAdminDto updateAdminPassword(String login, UpdateAdminDto userDto);
    UpdateUserDto updateUserPassword(String login, UpdateUserDto userDto);
}
