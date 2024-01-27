package com.example.zoo.storage.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.storage.service.StorageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    public static final String S3_REGEX_KEY_REGEX = "[^a-zA-Z\\d!\\-_.*`()]";
    private final AmazonS3 amazonS3;
    private final TransferManager transferManager;

    public StorageServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
    }

    @SneakyThrows
    @Override
    public byte[] downloadFile(String s3BucketName, String s3ObjectKey) {
        if (!amazonS3.doesObjectExist(s3BucketName, s3ObjectKey)) {
            throw new OperationException(ApiErrors.RESOURCE_NOT_FOUND);
        }
        final S3Object object = amazonS3.getObject(s3BucketName, s3ObjectKey);
        return object.getObjectContent().readAllBytes();
    }

    @Override
    public String uploadFile(String s3BucketName, MultipartFile multipartFile) {
        try {
            final PutObjectRequest putObjectRequest = getPutObjectRequest(s3BucketName, multipartFile);
            final Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForUploadResult();
            return putObjectRequest.getKey();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OperationException(ApiErrors.FAILED_S3_UPLOAD);
        }
    }

    @SneakyThrows
    private PutObjectRequest getPutObjectRequest(String s3BucketName, MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile)) {
            throw new OperationException(ApiErrors.EMPTY_FILE);
        }

        final String filename = Objects.requireNonNull(multipartFile.getOriginalFilename()).replaceAll(S3_REGEX_KEY_REGEX, "");
        final String s3Key = System.currentTimeMillis() + "-" + filename;

        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        return new PutObjectRequest(s3BucketName, s3Key, multipartFile.getInputStream(), metadata);
    }

    @Override
    public String updateFile(String s3BucketName, String s3ObjectKey, MultipartFile multipartFile) {
        deleteFile(s3BucketName, s3ObjectKey);
        return uploadFile(s3BucketName, multipartFile);
    }

    @Override
    public void deleteFile(String s3BucketName, String s3ObjectKey) {
        if (!amazonS3.doesObjectExist(s3BucketName, s3ObjectKey)) {
            throw new OperationException(ApiErrors.RESOURCE_NOT_FOUND);
        }
        amazonS3.deleteObject(s3BucketName, s3ObjectKey);
    }
}
