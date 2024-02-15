package com.example.zoo.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.zoo.data.CountryData;
import com.example.zoo.dto.CountryDTO;
import com.example.zoo.entity.Country;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.mapper.CountryMapper;
import com.example.zoo.repository.CountryRepository;
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

class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<CountryDTO> countryDTOList = new ArrayList<>();
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<CountryDTO> result = countryService.getAll();

        // Assert
        assertEquals(countryDTOList.size(), result.size());
    }

    @Test
    void testSave() throws IOException {
        // Arrange
        CountryData countryData = new CountryData();
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(countryRepository.saveAndFlush(any())).thenReturn(new Country());

        // Act & Assert
        assertDoesNotThrow(() -> countryService.save(countryData, multipartFile));
    }

    @Test
    void testUpdate() throws IOException {
        // Arrange
        Long id = 1L;
        CountryData countryData = new CountryData();
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> countryService.update(id, countryData, multipartFile));
    }

    @Test
    void testGetById() {
        // Arrange
        Long id = 1L;
        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> countryService.getById(id));
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> countryService.delete(id));
    }
}
