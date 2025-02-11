package com.ldubgd.emailService.repositories;

import com.ldubgd.components.dao.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

}
