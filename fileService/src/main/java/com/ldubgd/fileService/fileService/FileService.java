package com.ldubgd.fileService.fileService;

import com.ldubgd.fileService.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileInfo getFile(String id);

    void saveFile(MultipartFile file, Long statementId);
}
