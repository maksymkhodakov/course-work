package com.example.zoo.storage.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

class S3ServiceImplTest {

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private TransferManager transferManager;

    @InjectMocks
    private S3ServiceImpl s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDownloadFile() throws IOException {
        // Arrange
        String s3BucketName = "test-bucket";
        String s3ObjectKey = "test-object-key";
        byte[] content = "Test content".getBytes();
        S3Object s3Object = new S3Object();
        S3ObjectInputStream inputStream = new S3ObjectInputStream(new ByteArrayInputStream(content), null);
        s3Object.setObjectContent(inputStream);
        when(amazonS3.doesObjectExist(s3BucketName, s3ObjectKey)).thenReturn(true);
        when(amazonS3.getObject(s3BucketName, s3ObjectKey)).thenReturn(s3Object);

        // Act
        byte[] result = s3Service.downloadFile(s3BucketName, s3ObjectKey);

        // Assert
        assertArrayEquals(content, result);
    }

    @Test
    void testDeleteFile() {
        // Arrange
        String s3BucketName = "test-bucket";
        String s3ObjectKey = "test-object-key";
        when(amazonS3.doesObjectExist(s3BucketName, s3ObjectKey)).thenReturn(true);

        // Act
        s3Service.deleteFile(s3BucketName, s3ObjectKey);

        // Assert
        verify(amazonS3).deleteObject(s3BucketName, s3ObjectKey);
    }
}
