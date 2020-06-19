package com.intern.book.services;

import com.intern.book.properties.FileStorageProperties;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public void FileStorageService(FileStorageProperties fileStorageProperties);


    public String storeFile(MultipartFile file);
}
