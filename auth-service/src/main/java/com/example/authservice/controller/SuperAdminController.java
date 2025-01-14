package com.example.authservice.controller;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.UpdateAdminDto;
import com.example.authservice.entity.Admin;
import com.example.authservice.exception.RecourseNotFoundException;
import com.example.authservice.service.SuperAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/admin")
public class SuperAdminController {

    private final SuperAdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AdminDto adminDto) {
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
    public ResponseEntity<List<Admin>> getUser() {
        log.info("Fetching all users");
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/deleteByLogin/{login}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String login) {
        try {
            adminService.deleteAdminByLogin(login);
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
}
