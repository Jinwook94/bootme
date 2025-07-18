package com.bootme.image.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.ExternalServiceException;
import com.bootme.common.exception.FileHandlingException;
import com.bootme.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.bootme.common.exception.ErrorType.*;

@RequiredArgsConstructor
@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final AuthService authService;
    private final S3Client s3Client;

    @Value("${app.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${app.aws.region:ap-northeast-2}")
    private String awsRegion;

    private static final String PROFILE = "profile";
    private static final String COURSE_DETAIL = "courseDetail";
    private static final String POST = "post";
    private static final String POST_COMMENT = "postComment";

    public String upload(AuthInfo authInfo, String imageType, MultipartFile imageFile) {
        authService.validateLogin(authInfo);
        Long memberId = authInfo.getMemberId();

        logger.info("Uploading image to bucket: {} in region: {}", bucketName, awsRegion);

        File file = toFile(imageFile);
        String fileName = getFormattedFileName(imageType, memberId, file);
        String imageUrl = uploadToS3(fileName, file);

        deleteLocalTempFile(file);
        return imageUrl;
    }

    private File toFile(MultipartFile image) {
        File file = new File("/tmp/"+image.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            throw new FileHandlingException(FILE_CONVERSION_FAIL, image.getOriginalFilename(), e);
        }
        return file;
    }

    private String getFormattedFileName(String imageType, Long memberId, File image) {
        String uploadPath = switch (imageType) {
            case PROFILE -> String.format("profile/%d/", memberId);
            case COURSE_DETAIL -> String.format("course-detail/%d/", memberId);
            case POST -> String.format("post/%d/", memberId);
            case POST_COMMENT -> String.format("post-comment/%d/", memberId);
            default -> throw new ValidationException(INVALID_IMAGE_TYPE, imageType);
        };

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String currentDateTime = LocalDateTime.now().format(formatter);

        String originalFileName = image.getName();
        String fileNameWithTime = currentDateTime + "_" + originalFileName;

        return String.format("%s%s", uploadPath, fileNameWithTime);
    }

    private String uploadToS3(String fileName, File image) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(image));
            logger.info("Successfully uploaded file: {} to bucket: {}", fileName, bucketName);
        } catch (S3Exception e) {
            logger.error("Failed to upload file to S3. Bucket: {}, Error: {}", bucketName, e.getMessage());
            throw new ExternalServiceException(S3_UPLOAD_FAIL, e.getMessage());
        }

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, fileName);
    }

    private void deleteLocalTempFile(File image) {
        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (IOException e) {
            throw new FileHandlingException(FILE_DELETE_FAIL, e.getMessage());
        }
    }

}
