package com.bootme.image.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.utils.Login;
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
    public ResponseEntity<String> upload(@Login AuthInfo authInfo,
                                                @RequestParam String imageType,
                                                @RequestPart("image") MultipartFile imageFile) {
        String imageUrl = imageService.upload(authInfo, imageType, imageFile);
        return ResponseEntity.ok(imageUrl);
    }

}
