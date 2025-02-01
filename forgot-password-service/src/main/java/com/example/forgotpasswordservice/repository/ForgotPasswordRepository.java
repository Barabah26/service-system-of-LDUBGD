package com.example.forgotpasswordservice.repository;

import com.ldubgd.components.dao.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
}
