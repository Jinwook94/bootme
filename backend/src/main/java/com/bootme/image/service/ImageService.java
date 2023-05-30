package com.bootme.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bootme.common.exception.AwsException;
import com.bootme.common.exception.FileHandlingException;
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

    private final ImageConverter imageConverter;
    private final AmazonS3 amazonS3Client;

    public String upload(String courseId, MultipartFile image) {
        File file = toFile(image);
        File convertedFile = imageConverter.convertToProgressive(file);
        String fileName = getFormattedFileName(courseId, convertedFile);

        String savedUrl = uploadToS3(fileName, convertedFile);

        deleteTemporaryFile(convertedFile);
        return savedUrl;
    }

    private String getFormattedFileName(String courseId, File image) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String currentDateTime = LocalDateTime.now().format(formatter);

        String originalFileName = image.getName();
        String formattedFileName = currentDateTime + "_" + originalFileName;

        String uploadPath = String.format("course-detail/%s/", courseId);
        return String.format("%s%s", uploadPath, formattedFileName);
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

    private String uploadToS3(String fileName, File image) {
        String bucketName = "bootme-images";
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, image));
        } catch (AmazonS3Exception e) {
            throw new AwsException(S3_UPLOAD_FAIL, e.getMessage());
        }
        return String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucketName, fileName);
    }

    private void deleteTemporaryFile(File image) {
        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (IOException e) {
            throw new FileHandlingException(FILE_DELETE_FAIL, e.getMessage());
        }
    }

}
