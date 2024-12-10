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
    public ResponseEntity<byte[]> getDoc(@RequestParam("id") String hashId) {
        log.info("Отримано запит на завантаження файлу з hashId: " + hashId);

        FileInfo doc;
        try {
            doc = fileService.getFile(hashId);
        } catch (RuntimeException e) {
            log.error("Помилка при отриманні файлу: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        log.info("Файл успішно знайдено: " + doc.getFileName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(doc.getFileType()));

        // Кодуємо ім'я файлу для заголовка Content-Disposition
        String encodedFilename;
        try {
            encodedFilename = URLEncoder.encode(doc.getFileName(), StandardCharsets.UTF_8.toString());
            log.debug("Закодовано ім'я файлу: " + encodedFilename);
        } catch (UnsupportedEncodingException e) {
            log.error("Помилка при кодуванні імені файлу: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        headers.setContentDispositionFormData("attachment", encodedFilename);

        log.info("Формування відповіді з файлом: " + encodedFilename);
        return ResponseEntity.ok()
                .headers(headers)
                .body(doc.getFileData().getData());
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam Long statementId, @RequestParam("file") MultipartFile file) {
        log.info("Uploading file: {} for statement: {}", file.getOriginalFilename(), statementId);

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            fileService.saveFile(file, statementId);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (Exception e) {
            log.error("File upload error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}

