package com.ldubgd.fileService.dao;

import com.ldubgd.fileService.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBinaryContentRepository extends JpaRepository<FileData,Long> {
}
