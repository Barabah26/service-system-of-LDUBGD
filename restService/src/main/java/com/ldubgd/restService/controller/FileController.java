package com.ldubgd.restService.controller;

import com.ldubgd.restService.entity.FileInfo;
import com.ldubgd.restService.fileService.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Slf4j
@RequestMapping("/file")
@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/get-doc")
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
}

