package com.bootme.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.AwsException;
import com.bootme.common.exception.FileHandlingException;
import com.bootme.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.*;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final AuthService authService;
    private final AmazonS3 amazonS3Client;

    private static final String PROFILE = "profile";
    private static final String COURSE_DETAIL = "courseDetail";
    private static final String POST = "post";
    private static final String POST_COMMENT = "postComment";

    public String upload(AuthInfo authInfo, String imageType, MultipartFile imageFile) {
        authService.validateLogin(authInfo);
        Long memberId = authInfo.getMemberId();

        File file = toFile(imageFile);
        String fileName = getFormattedFileName(imageType, memberId, file);
        String imageUrl = uploadToS3(fileName, file);

        deleteLocalTempFile(file);
        return imageUrl;
    }

    private File toFile(MultipartFile image) {
        File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            throw new FileHandlingException(FILE_CONVERSION_FAIL, e.getMessage());
        }
        return file;
    }

    private String getFormattedFileName(String imageType, Long memberId, File image) {
        String uploadPath;
        switch (imageType) {
            case PROFILE:
                uploadPath = String.format("profile/%d/", memberId);
                break;
            case COURSE_DETAIL:
                uploadPath = String.format("course-detail/%d/", memberId);
                break;
            case POST:
                uploadPath = String.format("post/%d/", memberId);
                break;
            case POST_COMMENT:
                uploadPath = String.format("post-comment/%d/", memberId);
                break;
            default:
                throw new ValidationException(INVALID_IMAGE_TYPE, imageType);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String currentDateTime = LocalDateTime.now().format(formatter);

        String originalFileName = image.getName();
        String fileNameWithTime = currentDateTime + "_" + originalFileName;

        return String.format("%s%s", uploadPath, fileNameWithTime);
    }

    private String uploadToS3(String fileName, File image) {
        String bucketName = "bootme-images";
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, image));
        } catch (AmazonS3Exception e) {
            throw new AwsException(S3_UPLOAD_FAIL, e.getMessage());
        }
        return String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucketName, fileName);
    }

    private void deleteLocalTempFile(File image) {
        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (IOException e) {
            throw new FileHandlingException(FILE_DELETE_FAIL, e.getMessage());
        }
    }

}
