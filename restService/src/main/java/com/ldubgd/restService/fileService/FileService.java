package com.ldubgd.restService.fileService;

import com.ldubgd.restService.entity.FileInfo;
import org.springframework.core.io.FileSystemResource;

public interface FileService {

    FileInfo getFile(String id);

}
