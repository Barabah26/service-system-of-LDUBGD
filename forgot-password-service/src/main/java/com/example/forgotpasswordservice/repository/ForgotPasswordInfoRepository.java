package com.example.forgotpasswordservice.repository;

import com.ldubgd.components.dao.ForgotPasswordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordInfoRepository extends JpaRepository<ForgotPasswordInfo, Long> {
}
