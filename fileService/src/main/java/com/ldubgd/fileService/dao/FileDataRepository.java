package com.ldubgd.fileService.dao;

import com.ldubgd.components.dao.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO file_data (data, file_info_id) " +
            "VALUES (:data, :fileInfoId) " +
            "ON CONFLICT (file_info_id) DO UPDATE SET data = EXCLUDED.data", nativeQuery = true)
    void saveFileData(@Param("data") byte[] data, @Param("fileInfoId") Long fileInfoId);

}

