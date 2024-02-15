package com.example.zoo.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.zoo.data.AnimalData;
import com.example.zoo.dto.AnimalDTO;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.mapper.AnimalMapper;
import com.example.zoo.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    @InjectMocks
    private AnimalServiceImpl animalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<AnimalDTO> animalDTOList = new ArrayList<>();
        when(animalRepository.findAll()).thenReturn(new ArrayList<>());
        when(animalMapper.entityToDto(any())).thenReturn(new AnimalDTO());

        // Act
        List<AnimalDTO> result = animalService.getAll();

        // Assert
        assertEquals(animalDTOList.size(), result.size());
    }

    @Test
    void testUpdate() throws IOException {
        // Arrange
        Long id = 1L;
        AnimalData animalData = new AnimalData();
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> animalService.update(id, animalData, multipartFile));
    }

    @Test
    void testGetById() {
        // Arrange
        Long id = 1L;
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> animalService.getById(id));
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> animalService.delete(id));
    }

    @Test
    void testGetRelatedCountries() {
        // Arrange
        Long id = 1L;
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> animalService.getRelatedCountries(id));
    }

    @Test
    void testDeleteCountry() {
        // Arrange
        Long countryId = 1L;
        Long id = 1L;
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> animalService.deleteCountry(countryId, id));
    }

    @Test
    void testAddCountry() {
        // Arrange
        Long animalId = 1L;
        Long countryId = 1L;
        when(animalRepository.findById(animalId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> animalService.addCountry(animalId, countryId));
    }
}
