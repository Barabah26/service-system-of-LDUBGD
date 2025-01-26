package com.ldubgd.emailService.repositories;

import com.ldubgd.components.dao.Statement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {


    @EntityGraph(attributePaths = {"user","statementInfo"})
    Optional<Statement> findById(Long id);

}
