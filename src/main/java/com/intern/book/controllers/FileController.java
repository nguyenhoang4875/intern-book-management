package com.intern.book.controllers;

import com.intern.book.models.dto.UploadFileResponse;
import com.intern.book.services.FileStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        return new UploadFileResponse(fileName, file.getContentType(), file.getSize(), "SUCCESS");
    }

    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<byte[]> getFile(@PathVariable String name) throws IOException {
        System.out.println("name: "+name);
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/images/" + name);
        return ResponseEntity.ok().body(IOUtils.toByteArray(in));
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

}
