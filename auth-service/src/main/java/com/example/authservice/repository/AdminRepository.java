package com.example.authservice.repository;

import com.example.authservice.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByLogin(String login);

    Optional<Admin> findByRole(String role);
}
