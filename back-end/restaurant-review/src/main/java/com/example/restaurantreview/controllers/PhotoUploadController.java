package com.example.restaurantreview.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * PhotoUploadController отвечает за обработку загрузки фотографий ресторанов.
 * Предоставляет конечную точку для загрузки фотографий, которые затем сохраняются локально.
 */
@Controller
public class PhotoUploadController {

    @Value("${upload.directory}")
    private String uploadDir;

    /**
     * Конечная точка для обработки загрузки фотографий.
     * @param file Загружаемый файл фотографии
     * @return ResponseEntity<String> Ответ, указывающий на успешность или неудачу загрузки
     */
    @PostMapping("/resources/restaurant-photos")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        try {
            // Создаем папку, если ее нет
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Сохраняем файл на сервере
            String fileName = file.getOriginalFilename();
            File destFile = new File(uploadDir + "/" + fileName);
            file.transferTo(destFile);

            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}
