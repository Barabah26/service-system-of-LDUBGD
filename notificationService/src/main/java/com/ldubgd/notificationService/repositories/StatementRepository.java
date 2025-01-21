package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.Statement;
import com.ldubgd.components.dao.enums.StatementStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {


    @EntityGraph(attributePaths = {"user","statementInfo" })
    @Query("SELECT s FROM Statement s WHERE s.statementInfo.statementStatus = :status AND s.statementInfo.isReady = :isReady")
    List<Statement> findStatementsByStatusAndIsReady(@Param("status") StatementStatus status, @Param("isReady") Boolean isReady);

}
