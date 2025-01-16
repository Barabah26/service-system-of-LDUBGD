package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

}
