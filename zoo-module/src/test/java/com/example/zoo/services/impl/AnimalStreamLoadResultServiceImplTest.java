package com.example.zoo.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.zoo.dto.AnimalStreamLoadResultDTO;
import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import com.example.zoo.storage.config.AWSProperties;
import com.example.zoo.storage.service.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AnimalStreamLoadResultServiceImplTest {

    @Mock
    private AWSProperties awsProperties;

    @Mock
    private S3Service s3Service;

    @Mock
    private AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @InjectMocks
    private AnimalStreamLoadResultServiceImpl animalStreamLoadResultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<AnimalStreamLoadResult> loadResults = new ArrayList<>();
        when(animalStreamLoadResultRepository.findAll()).thenReturn(loadResults);

        // Act
        List<AnimalStreamLoadResultDTO> result = animalStreamLoadResultService.getAll();

        // Assert
        assertEquals(loadResults.size(), result.size());
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        AnimalStreamLoadResult loadToDelete = new AnimalStreamLoadResult();
        when(animalStreamLoadResultRepository.findById(id)).thenReturn(java.util.Optional.of(loadToDelete));

        // Act
        animalStreamLoadResultService.delete(id);

        // Assert
        verify(animalStreamLoadResultRepository, times(1)).delete(eq(loadToDelete));
        verify(s3Service, times(0)).deleteFile(any(), any());
    }

    @Test
    void testSave() {
        // Arrange
        AnimalStreamProcessType processType = AnimalStreamProcessType.KAFKA;
        MultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test data".getBytes());

        // Act
        assertDoesNotThrow(() -> animalStreamLoadResultService.save(processType, file));

        // Assert
        verify(animalStreamLoadResultRepository, times(1)).save(any(AnimalStreamLoadResult.class));
        verify(s3Service, times(1)).uploadFile(any(), any());
    }

    @Test
    void testGetResource() {
        // Arrange
        Long id = 1L;
        AnimalStreamLoadResult load = new AnimalStreamLoadResult();
        when(animalStreamLoadResultRepository.findById(id)).thenReturn(java.util.Optional.of(load));
        byte[] bytes = "test data".getBytes();
        when(s3Service.downloadFile(anyString(), anyString())).thenReturn(bytes);

        // Act
        ResponseEntity<byte[]> response = animalStreamLoadResultService.getResource(id);

        // Assert
        assertNull(response.getBody());
    }
}
