package com.devano.blog_app.controller;

import com.devano.blog_app.service.CloudStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class MediaController {
    CloudStorageService cloudStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try{
            cloudStorageService.uploadFile(file);
        }catch (IOException e) {
            return ResponseEntity.ok().body("gagal upload file");
        }
        return ResponseEntity.ok().body("okk");
    }
}
