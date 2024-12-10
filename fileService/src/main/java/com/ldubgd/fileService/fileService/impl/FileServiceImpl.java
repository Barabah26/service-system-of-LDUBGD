package com.ldubgd.fileService.fileService.impl;

import com.ldubgd.fileService.dao.FileDataRepository;
import com.ldubgd.fileService.dao.FileInfoRepository;
import com.ldubgd.fileService.dao.JpaAppDocumentRepository;
import com.ldubgd.fileService.dao.StatementRepository;
import com.ldubgd.fileService.entity.FileInfo;
import com.ldubgd.fileService.fileService.FileService;
import com.ldubgd.utils.CryptoTool;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final JpaAppDocumentRepository jpaAppDocumentRepository;
    private final CryptoTool cryptoTool;

    private final FileDataRepository fileDataRepository;
    private final FileInfoRepository fileInfoRepository;
    private final StatementRepository statementInfoRepository;



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



    @Override
    public void saveFile(MultipartFile file, Long statementId) {

        Long savedFileInfoId = fileInfoRepository.saveOrUpdateFileInfo(
                file.getOriginalFilename(),
                file.getContentType(),
                statementId
        );

        try {
            byte[] fileDataBytes = file.getBytes();
            fileDataRepository.saveFileData(fileDataBytes, savedFileInfoId);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing file data", e);
        }
    }
}

