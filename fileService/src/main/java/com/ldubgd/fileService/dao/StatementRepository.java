package com.ldubgd.fileService.dao;

import com.ldubgd.fileService.entity.StatementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link StatementInfo} entities.
 */
@Repository
public interface StatementRepository extends JpaRepository<StatementInfo, Long> {



}
