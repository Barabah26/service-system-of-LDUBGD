package com.ldubgd.fileService.fileService;

import com.ldubgd.components.dao.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileInfo getFile(Long id);

    void saveFile(MultipartFile file, Long statementId);
}
