package com.ldubgd.fileService.controller;

import com.ldubgd.components.dao.FileInfo;
import com.ldubgd.fileService.fileService.FileService;
import com.ldubgd.utils.CryptoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequestMapping("/file")
@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @Autowired
    private final CryptoTool cryptoTool;

    @GetMapping("/download")
    public ResponseEntity<?> getFile(@RequestParam("id") String hashId) {
        log.info("Отримано запит на завантаження файлу з hashId: {}", hashId);

        // Перетворення hashId у числове значення
        Long statementId;
        try {
            statementId = cryptoTool.idOf(hashId);
        } catch (NumberFormatException ex) {
            log.error("Невірний формат hashId: {}", hashId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Невірний формат id");
        }

        // Отримання інформації про файл за id заявки
        FileInfo fileInfo;
        try {
            fileInfo = fileService.getFile(statementId);
            log.info("Файл знайдено: {}", fileInfo.getFileName());
        } catch (RuntimeException ex) {
            log.error("Помилка при отриманні файлу: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не знайдено");
        }

        // Налаштування HTTP заголовків
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileInfo.getFileType()));

        String encodedFilename;
        try {
            encodedFilename = URLEncoder.encode(fileInfo.getFileName(), StandardCharsets.UTF_8.toString());
            log.debug("Закодовано ім'я файлу: {}", encodedFilename);
        } catch (UnsupportedEncodingException ex) {
            log.error("Помилка при кодуванні імені файлу: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Помилка сервера");
        }

        headers.setContentDispositionFormData("attachment", encodedFilename);
        log.info("Підготовлено відповідь для завантаження файлу: {}", encodedFilename);

        // Повернення файлу як byte[]
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileInfo.getFileData().getData());
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("id") Long statementId, @RequestParam("file") MultipartFile file) {
        log.info("Отримано запит на завантаження файлу: {} для statementId: {}", file.getOriginalFilename(), statementId);

        if (file.isEmpty()) {
            log.warn("Спроба завантажити порожній файл для statementId: {}", statementId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Файл порожній");
        }

        try {
            fileService.saveFile(file, statementId);
            log.info("Файл успішно завантажено: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.CREATED).body("Файл успішно завантажено");
        } catch (RuntimeException e) {
            log.error("Помилка при завантаженні файлу для statementId {}: {}", statementId, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Помилка при обробці запиту: " + e.getMessage());
        } catch (Exception e) {
            log.error("Невідома помилка при завантаженні файлу для statementId {}: {}", statementId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Невідома помилка: " + e.getMessage());
        }
    }
}
