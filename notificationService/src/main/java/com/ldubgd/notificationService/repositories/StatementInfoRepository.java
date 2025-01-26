package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.StatementInfo;
import com.ldubgd.components.dao.enums.StatementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatementInfoRepository extends JpaRepository<StatementInfo, Long> {

    List<StatementInfo> findStatementInfosByIsReadyAndStatementStatus(Boolean isReady, StatementStatus statementStatus);
}
