package com.ldubgd.restService.fileService.impl;

import com.ldubgd.restService.dao.JpaAppDocumentRepository;
import com.ldubgd.restService.entity.FileInfo;
import com.ldubgd.restService.fileService.FileService;
import com.ldubgd.utils.CryptoTool;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final JpaAppDocumentRepository jpaAppDocumentRepository;
    private final CryptoTool cryptoTool;

    @Override
    public FileInfo getFile(String hashId) {
        log.info("Отримання файлу за hashId: {}", hashId);

        Long id = cryptoTool.idOf(hashId);
        log.debug("Перетворено hashId на id: {}", id);

        Optional<FileInfo> fileInfo = jpaAppDocumentRepository.findByStatementId(id);

        if (fileInfo.isPresent()) {
            log.info("Файл знайдено для id: {}", id);
            return fileInfo.get();
        } else {
            log.error("Файл за id не знайдено: {}", id);
            throw new RuntimeException("Файл за id не знайдено");
        }
    }
}

