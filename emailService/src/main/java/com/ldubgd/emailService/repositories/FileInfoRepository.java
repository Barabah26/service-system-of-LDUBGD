package com.ldubgd.emailService.repositories;

import com.ldubgd.components.dao.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo,Long> {

}
