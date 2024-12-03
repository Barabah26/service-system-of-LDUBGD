package com.ldubgd.fileService.dao;

import com.ldubgd.fileService.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAppDocumentRepository extends JpaRepository<FileInfo,Long> {
    Optional<FileInfo> findByStatementId(Long id);

}
