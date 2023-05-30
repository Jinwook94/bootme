package com.bootme.image.controller;

import com.bootme.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam String courseId,
                                         @RequestPart("image") MultipartFile image) {
        String imageUrl = imageService.upload(courseId, image);
        return ResponseEntity.ok(imageUrl);
    }

}
