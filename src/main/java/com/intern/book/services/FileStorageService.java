package com.intern.book.services;

import com.intern.book.properties.FileStorageProperties;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void FileStorageService(FileStorageProperties fileStorageProperties);

    String storeFile(MultipartFile file);
}
