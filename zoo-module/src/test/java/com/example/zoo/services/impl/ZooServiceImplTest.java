package com.example.zoo.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.zoo.data.ZooData;
import com.example.zoo.dto.ZooDTO;
import com.example.zoo.entity.Country;
import com.example.zoo.entity.Zoo;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.mapper.AnimalMapper;
import com.example.zoo.mapper.ZooMapper;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.repository.CountryRepository;
import com.example.zoo.repository.ZooRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ZooServiceImplTest {

    @Mock
    private ZooRepository zooRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    @Spy
    private ZooMapper zooMapper;

    @InjectMocks
    private ZooServiceImpl zooService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<ZooDTO> zooDTOList = new ArrayList<>();
        when(zooRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ZooDTO> result = zooService.getAll();

        // Assert
        assertEquals(zooDTOList.size(), result.size());
    }

    @Test
    void testSave() {
        // Arrange
        ZooData zooData = new ZooData();
        when(countryRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.save(zooData));
    }

    @Test
    void testSaveSpy() {
        // Arrange
        ZooData zooData = new ZooData();
        zooData.setLocationId(1L); // Assume this is the ID for the location
        Country country = new Country(); // Assume Country is an entity
        country.setId(1L);

        when(countryRepository.findById(any())).thenReturn(Optional.of(country));
        doReturn(new Zoo()).when(zooMapper).dataToEntity(any(ZooData.class), any(Country.class)); // Stubbing method of spy

        // Act
        zooService.save(zooData);

        // Assert
        verify(zooRepository).saveAndFlush(any(Zoo.class)); // Verify saveAndFlush was called
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        ZooData zooData = new ZooData();
        when(zooRepository.findById(id)).thenReturn(Optional.empty());
        when(countryRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.update(id, zooData));
    }

    @Test
    void testGetById() {
        // Arrange
        Long id = 1L;
        when(zooRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.getById(id));
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        when(zooRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.delete(id));
    }

    @Test
    void testGetAllAnimals() {
        // Arrange
        Long id = 1L;
        when(zooRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.getAllAnimals(id));
    }

    @Test
    void testAddAnimal() {
        // Arrange
        Long id = 1L;
        Long animalId = 1L;
        when(zooRepository.findById(id)).thenReturn(Optional.empty());
        when(animalRepository.findById(animalId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.addAnimal(id, animalId));
    }

    @Test
    void testDeleteAnimal() {
        // Arrange
        Long id = 1L;
        Long animalId = 1L;
        when(zooRepository.findById(id)).thenReturn(Optional.empty());
        when(animalRepository.findById(animalId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OperationException.class, () -> zooService.deleteAnimal(id, animalId));
    }
}
