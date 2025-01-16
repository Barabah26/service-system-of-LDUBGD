package com.example.authservice.repository;

import com.ldubgd.components.dao.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByLogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE Admin a SET a.password = :newPassword WHERE a.login = :login")
    void updateUserByUsername(@Param("login") String login,
                              @Param("newPassword") String newPassword);
}
