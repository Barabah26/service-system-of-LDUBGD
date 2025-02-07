package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.ForgotPasswordInfo;
import com.ldubgd.components.dao.enums.StatementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForgotPasswordInfoRepository extends JpaRepository<ForgotPasswordInfo, Long> {
    List<ForgotPasswordInfo> findForgotPasswordInfosByIsReadyAndStatementStatus(Boolean isReady, StatementStatus statementStatus);

}
