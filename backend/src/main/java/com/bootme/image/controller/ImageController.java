package com.bootme.image.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.token.Login;
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
                                         @RequestParam String itemType,
                                         @RequestParam String itemId,
                                         @RequestPart("image") MultipartFile image) {
        String imageUrl = imageService.upload(authInfo.getMemberId(), itemType, itemId, image);
        return ResponseEntity.ok(imageUrl);
    }

}
