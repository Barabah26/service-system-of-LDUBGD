package com.ldubgd.fileService.controller;

import com.ldubgd.fileService.entity.FileInfo;
import com.ldubgd.fileService.fileService.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/file")
@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<?> getFile(@RequestParam("id") String hashId) {
        log.info("Отримано запит на завантаження файлу з hashId: {}", hashId);

        Long statementId;
        try {
            statementId = Long.parseLong(hashId);
        } catch (NumberFormatException e) {
            log.error("Невірний формат hashId: {}", hashId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не вірний формат id");
        }

        FileInfo doc;
        try {
            doc = fileService.getFile(statementId);
            log.info("Файл успішно знайдено: {}", doc.getFileName());
        } catch (RuntimeException e) {
            log.error("Помилка при отриманні файлу: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("файл не знайдено");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(doc.getFileType()));

        String encodedFilename;
        try {
            encodedFilename = URLEncoder.encode(doc.getFileName(), StandardCharsets.UTF_8.toString());
            log.debug("Закодовано ім'я файлу: {}", encodedFilename);
        } catch (UnsupportedEncodingException e) {
            log.error("Помилка при кодуванні імені файлу: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("помилка сервера");
        }

        headers.setContentDispositionFormData("attachment", encodedFilename);

        log.info("Формування відповіді з файлом: {}", encodedFilename);
        return ResponseEntity.ok()
                .headers(headers)
                .body(doc.getFileData().getData());
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
