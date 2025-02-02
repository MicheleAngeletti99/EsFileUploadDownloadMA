package com.example.esfileuploaddownload.services.controllers;

import com.example.esfileuploaddownload.services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/es")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        return fileStorageService.upload(file);
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        // prendo l'estensione del file
        String extension = FilenameUtils.getExtension(fileName);
        // inserisco il content type con uno switch, aggiungendo altri case nello switch posso gestire altri tipi di file
        switch (extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        // e devo impostare un header standard
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // restituisco il file
        return fileStorageService.download(fileName);
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam String fileName) throws IOException {
        fileStorageService.remove(fileName);
    }
}
