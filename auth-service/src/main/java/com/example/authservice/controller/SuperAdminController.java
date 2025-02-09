package com.example.authservice.controller;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.UpdateAdminDto;
import com.example.authservice.dto.UpdateUserDto;
import com.example.authservice.exception.RecourseNotFoundException;
import com.example.authservice.service.SuperAdminService;
import com.ldubgd.components.dao.Admin;
import com.ldubgd.components.dao.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/auth/admin")
public class SuperAdminController {

    private final SuperAdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminDto adminDto) {
        try {
            AdminDto registeredAdmin = adminService.registerAdmin(adminDto);
            return ResponseEntity.ok(registeredAdmin);
        } catch (RecourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/allAdmins")
    public ResponseEntity<List<Admin>> getAdmins() {
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = adminService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/deleteByLogin/{login}")
    public ResponseEntity<?> deleteAdminByUsername(@PathVariable String login) {
        try {
            adminService.deleteAdminByLogin(login);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteUserByLogin/{login}")
    public ResponseEntity<?> deleteUserByLogin(@PathVariable String login) {
        try {
            adminService.deleteUserByLogin(login);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/updateByLogin/{login}")
    public ResponseEntity<?> updateAdminByLogin(@PathVariable String login, @RequestBody UpdateAdminDto updateAdminDto) {
        try {
            UpdateAdminDto currentAdmin = adminService.updateAdminPassword(login, updateAdminDto);
            if (currentAdmin != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/updateUserPasswordByLogin/{login}")
    public ResponseEntity<?> updateUserPasswordByLogin(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) {
        try {
            UpdateUserDto currentUser = adminService.updateUserPassword(login, updateUserDto);
            if (currentUser != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
