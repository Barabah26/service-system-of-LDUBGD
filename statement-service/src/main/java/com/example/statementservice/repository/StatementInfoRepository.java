package com.example.statementservice.repository;

import com.example.statementservice.entity.StatementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementInfoRepository extends JpaRepository<StatementInfo, Long> {

}
