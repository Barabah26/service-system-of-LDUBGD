package com.ldubgd.restService.dao;

import com.ldubgd.restService.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBinaryContentRepository extends JpaRepository<FileData,Long> {
}
