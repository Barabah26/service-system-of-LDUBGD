package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.Statement;
import com.ldubgd.components.dao.StatementInfo;
import com.ldubgd.components.dao.enums.StatementStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatementInfoRepository extends JpaRepository<StatementInfo, Long> {





}
