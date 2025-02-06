package com.example.forgotpasswordservice.repository;

import com.ldubgd.components.dao.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE ForgotPassword f SET f.login = :login, f.password = :password WHERE f.id = :id AND (f.login IS NULL OR f.password IS NULL)")
    int updateIfNull(Long id, String login, String password);
}
