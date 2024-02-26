package org.example.consumermodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.AnimalStream;
import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import com.example.zoo.repository.AnimalStreamRepository;

import java.sql.Timestamp;
import java.util.*;

import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.dto.AnimalStreamFileDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalStreamServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamServiceImplTest {
    @MockBean
    private AnimalRepository animalRepository;

    @MockBean
    private AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @MockBean
    private AnimalStreamRepository animalStreamRepository;

    @Autowired
    private AnimalStreamServiceImpl animalStreamServiceImpl;

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams2() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any()))
                .thenThrow(new RuntimeException("Invalid kind"));

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl.processAnimalStreams(animalStreams));
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams3() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        AnimalStreamLoadResult loadResult2 = new AnimalStreamLoadResult();
        loadResult2.setAnimalStreams(new ArrayList<>());
        loadResult2.setCreateDate(mock(Timestamp.class));
        loadResult2.setFilename("Invalid kind");
        loadResult2.setId(2L);
        loadResult2.setLastUpdateDate(mock(Timestamp.class));
        loadResult2.setProcessType(AnimalStreamProcessType.RABBIT_MQ);
        loadResult2.setProcessed(false);
        loadResult2.setS3Link(Boolean.TRUE.toString());
        loadResult2.setToDelete(false);

        AnimalStream animalStream2 = new AnimalStream();
        animalStream2.setAge(Boolean.TRUE.toString());
        animalStream2.setCreateDate(mock(Timestamp.class));
        animalStream2.setErrorMessage("Invalid kind");
        animalStream2.setId(2L);
        animalStream2.setKindAnimal(Boolean.TRUE.toString());
        animalStream2.setLastUpdateDate(mock(Timestamp.class));
        animalStream2.setLoadResult(loadResult2);
        animalStream2.setName(Boolean.TRUE.toString());
        animalStream2.setProcessed(false);
        animalStream2.setTypePowerSupply(Boolean.TRUE.toString());
        animalStream2.setVenomous(Boolean.TRUE.toString());

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream2);
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams4() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams5() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        AnimalStreamLoadResult loadResult2 = new AnimalStreamLoadResult();
        loadResult2.setAnimalStreams(new ArrayList<>());
        loadResult2.setCreateDate(mock(Timestamp.class));
        loadResult2.setFilename(Boolean.TRUE.toString());
        loadResult2.setId(3L);
        loadResult2.setLastUpdateDate(mock(Timestamp.class));
        loadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult2.setProcessed(true);
        loadResult2.setS3Link(Boolean.FALSE.toString());
        loadResult2.setToDelete(true);

        AnimalStream animalStream2 = new AnimalStream();
        animalStream2.setAge(Boolean.FALSE.toString());
        animalStream2.setCreateDate(mock(Timestamp.class));
        animalStream2.setErrorMessage(Boolean.TRUE.toString());
        animalStream2.setId(3L);
        animalStream2.setKindAnimal(Boolean.FALSE.toString());
        animalStream2.setLastUpdateDate(mock(Timestamp.class));
        animalStream2.setLoadResult(loadResult2);
        animalStream2.setName(Boolean.FALSE.toString());
        animalStream2.setProcessed(true);
        animalStream2.setTypePowerSupply(Boolean.FALSE.toString());
        animalStream2.setVenomous(Boolean.FALSE.toString());

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream2);
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams6() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn(null);
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams7() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("42");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams8() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams9() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn(null);
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams10() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams11() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn(null);
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams12() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams13() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn(null);
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams14() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("");
        when(animalStream.getVenomous()).thenReturn("Venomous");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams15() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn(Boolean.TRUE.toString());
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream, atLeast(1)).setVenomous(Mockito.<String>any());
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams16() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn(null);
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#processAnimalStreams(List)}
     */
    @Test
    void testProcessAnimalStreams17() {
        // Arrange
        when(animalRepository.saveAll(Mockito.<Iterable<Animal>>any())).thenReturn(new ArrayList<>());
        when(animalStreamRepository.saveAll(Mockito.<Iterable<AnimalStream>>any())).thenReturn(new ArrayList<>());

        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getAge()).thenReturn("Age");
        when(animalStream.getErrorMessage()).thenReturn("An error occurred");
        when(animalStream.getKindAnimal()).thenReturn("Kind Animal");
        when(animalStream.getName()).thenReturn("Name");
        when(animalStream.getTypePowerSupply()).thenReturn("Type Power Supply");
        when(animalStream.getVenomous()).thenReturn("");
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");

        ArrayList<AnimalStream> animalStreams = new ArrayList<>();
        animalStreams.add(animalStream);

        // Act
        animalStreamServiceImpl.processAnimalStreams(animalStreams);

        // Assert
        verify(animalStream, atLeast(1)).getAge();
        verify(animalStream, atLeast(1)).getErrorMessage();
        verify(animalStream, atLeast(1)).getKindAnimal();
        verify(animalStream, atLeast(1)).getName();
        verify(animalStream, atLeast(1)).getTypePowerSupply();
        verify(animalStream, atLeast(1)).getVenomous();
        verify(animalStream).setAge(eq("Age"));
        verify(animalStream, atLeast(1)).setErrorMessage(Mockito.<String>any());
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(eq("Name"));
        verify(animalStream, atLeast(1)).setProcessed(Mockito.<Boolean>any());
        verify(animalStream).setTypePowerSupply(eq("Type Power Supply"));
        verify(animalStream).setVenomous(eq("Venomous"));
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalRepository, times(1)).saveAll(Mockito.<Iterable<Animal>>any());
        verify(animalStreamRepository, times(1)).saveAll(Mockito.<Iterable<AnimalStream>>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#saveLoad(AnimalStreamDTO)}
     */
    @Test
    void testSaveLoad() {
        // Arrange
        Optional<AnimalStreamLoadResult> emptyResult = Optional.empty();
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(OperationException.class, () -> animalStreamServiceImpl.saveLoad(new AnimalStreamDTO()));
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#saveLoad(AnimalStreamDTO)}
     */
    @Test
    void testSaveLoad2() {
        // Arrange
        AnimalStreamLoadResult animalStreamLoadResult = new AnimalStreamLoadResult();
        animalStreamLoadResult.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult.setFilename("foo.txt");
        animalStreamLoadResult.setId(1L);
        animalStreamLoadResult.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult.setProcessed(true);
        animalStreamLoadResult.setS3Link("S3 Link");
        animalStreamLoadResult.setToDelete(true);
        Optional<AnimalStreamLoadResult> ofResult = Optional.of(animalStreamLoadResult);

        AnimalStreamLoadResult animalStreamLoadResult2 = new AnimalStreamLoadResult();
        animalStreamLoadResult2.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult2.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setFilename("foo.txt");
        animalStreamLoadResult2.setId(1L);
        animalStreamLoadResult2.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult2.setProcessed(true);
        animalStreamLoadResult2.setS3Link("S3 Link");
        animalStreamLoadResult2.setToDelete(true);
        when(animalStreamLoadResultRepository.save(Mockito.<AnimalStreamLoadResult>any()))
                .thenReturn(animalStreamLoadResult2);
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AnimalStreamDTO animalStreamDTO = new AnimalStreamDTO();
        animalStreamDTO.setAnimalStream(new HashSet<>());

        // Act
        animalStreamServiceImpl.saveLoad(animalStreamDTO);

        // Assert
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
        verify(animalStreamLoadResultRepository, times(1)).save(Mockito.<AnimalStreamLoadResult>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#saveLoad(AnimalStreamDTO)}
     */
    @Test
    void testSaveLoad3() {
        // Arrange
        AnimalStreamLoadResult animalStreamLoadResult = new AnimalStreamLoadResult();
        animalStreamLoadResult.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult.setFilename("foo.txt");
        animalStreamLoadResult.setId(1L);
        animalStreamLoadResult.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult.setProcessed(true);
        animalStreamLoadResult.setS3Link("S3 Link");
        animalStreamLoadResult.setToDelete(true);
        Optional<AnimalStreamLoadResult> ofResult = Optional.of(animalStreamLoadResult);

        AnimalStreamLoadResult animalStreamLoadResult2 = new AnimalStreamLoadResult();
        animalStreamLoadResult2.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult2.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setFilename("foo.txt");
        animalStreamLoadResult2.setId(1L);
        animalStreamLoadResult2.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult2.setProcessed(true);
        animalStreamLoadResult2.setS3Link("S3 Link");
        animalStreamLoadResult2.setToDelete(true);
        when(animalStreamLoadResultRepository.save(Mockito.<AnimalStreamLoadResult>any()))
                .thenReturn(animalStreamLoadResult2);
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Set<AnimalStreamFileDTO> animalStream = new HashSet<>();
        animalStream.add(new AnimalStreamFileDTO(1, "Name", "Kind Animal", "Venomous", "Type Power Supply", "Age"));

        AnimalStreamDTO animalStreamDTO = new AnimalStreamDTO();
        animalStreamDTO.setAnimalStream(animalStream);

        // Act
        animalStreamServiceImpl.saveLoad(animalStreamDTO);

        // Assert
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
        verify(animalStreamLoadResultRepository, times(1)).save(Mockito.<AnimalStreamLoadResult>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#saveLoad(AnimalStreamDTO)}
     */
    @Test
    void testSaveLoad4() {
        // Arrange
        AnimalStreamLoadResult animalStreamLoadResult = new AnimalStreamLoadResult();
        animalStreamLoadResult.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult.setFilename("foo.txt");
        animalStreamLoadResult.setId(1L);
        animalStreamLoadResult.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult.setProcessed(true);
        animalStreamLoadResult.setS3Link("S3 Link");
        animalStreamLoadResult.setToDelete(true);
        Optional<AnimalStreamLoadResult> ofResult = Optional.of(animalStreamLoadResult);

        AnimalStreamLoadResult animalStreamLoadResult2 = new AnimalStreamLoadResult();
        animalStreamLoadResult2.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult2.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setFilename("foo.txt");
        animalStreamLoadResult2.setId(1L);
        animalStreamLoadResult2.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult2.setProcessed(true);
        animalStreamLoadResult2.setS3Link("S3 Link");
        animalStreamLoadResult2.setToDelete(true);
        when(animalStreamLoadResultRepository.save(Mockito.<AnimalStreamLoadResult>any()))
                .thenReturn(animalStreamLoadResult2);
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Set<AnimalStreamFileDTO> animalStream = new HashSet<>();
        animalStream.add(new AnimalStreamFileDTO(1, "Name", "Kind Animal", "Venomous", "Type Power Supply", "Age"));
        animalStream.add(new AnimalStreamFileDTO(1, "Name", "Kind Animal", "Venomous", "Type Power Supply", "Age"));

        AnimalStreamDTO animalStreamDTO = new AnimalStreamDTO();
        animalStreamDTO.setAnimalStream(animalStream);

        // Act
        animalStreamServiceImpl.saveLoad(animalStreamDTO);

        // Assert
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
        verify(animalStreamLoadResultRepository).save(Mockito.<AnimalStreamLoadResult>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#saveLoad(AnimalStreamDTO)}
     */
    @Test
    void testSaveLoad5() {
        // Arrange
        AnimalStreamLoadResult animalStreamLoadResult = new AnimalStreamLoadResult();
        animalStreamLoadResult.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult.setFilename("foo.txt");
        animalStreamLoadResult.setId(1L);
        animalStreamLoadResult.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult.setProcessed(true);
        animalStreamLoadResult.setS3Link("S3 Link");
        animalStreamLoadResult.setToDelete(true);
        Optional<AnimalStreamLoadResult> ofResult = Optional.of(animalStreamLoadResult);
        when(animalStreamLoadResultRepository.save(Mockito.<AnimalStreamLoadResult>any()))
                .thenThrow(new RuntimeException("foo"));
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AnimalStreamDTO animalStreamDTO = new AnimalStreamDTO();
        animalStreamDTO.setAnimalStream(new HashSet<>());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl.saveLoad(animalStreamDTO));
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
        verify(animalStreamLoadResultRepository, times(1)).save(Mockito.<AnimalStreamLoadResult>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#save(AnimalDTO)}
     */
    @Test
    void testSave() {
        // Arrange
        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");
        when(animalStreamRepository.save(Mockito.<AnimalStream>any())).thenReturn(animalStream);

        // Act
        animalStreamServiceImpl.save(AnimalStreamProcessType.RABBIT_MQ, new AnimalDTO("Name", "Kind Animal", "Venomous", "Type Power Supply", "Age"));

        // Assert
        verify(animalStreamRepository, times(1)).save(Mockito.<AnimalStream>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#(AnimalDTO)}
     */
    @Test
    void testSave2() {
        // Arrange
        when(animalStreamRepository.save(Mockito.<AnimalStream>any()))
                .thenThrow(new RuntimeException("Consumer received message value: {}"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl
                .save(AnimalStreamProcessType.KAFKA, new AnimalDTO("Name", "Kind Animal", "Venomous", "Type Power Supply", "Age")));
        verify(animalStreamRepository).save(Mockito.<AnimalStream>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#update(AnimalUpdateDTO)}
     */
    @Test
    void testUpdate() {
        // Arrange
        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");
        Optional<AnimalStream> ofResult = Optional.of(animalStream);

        AnimalStreamLoadResult loadResult2 = new AnimalStreamLoadResult();
        loadResult2.setAnimalStreams(new ArrayList<>());
        loadResult2.setCreateDate(mock(Timestamp.class));
        loadResult2.setFilename("foo.txt");
        loadResult2.setId(1L);
        loadResult2.setLastUpdateDate(mock(Timestamp.class));
        loadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult2.setProcessed(true);
        loadResult2.setS3Link("S3 Link");
        loadResult2.setToDelete(true);

        AnimalStream animalStream2 = new AnimalStream();
        animalStream2.setAge("Age");
        animalStream2.setCreateDate(mock(Timestamp.class));
        animalStream2.setErrorMessage("An error occurred");
        animalStream2.setId(1L);
        animalStream2.setKindAnimal("Kind Animal");
        animalStream2.setLastUpdateDate(mock(Timestamp.class));
        animalStream2.setLoadResult(loadResult2);
        animalStream2.setName("Name");
        animalStream2.setProcessed(true);
        animalStream2.setTypePowerSupply("Type Power Supply");
        animalStream2.setVenomous("Venomous");
        when(animalStreamRepository.save(Mockito.<AnimalStream>any())).thenReturn(animalStream2);
        when(animalStreamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        animalStreamServiceImpl.update(new AnimalUpdateDTO());

        // Assert
        verify(animalStreamRepository).findById(Mockito.<Long>any());
        verify(animalStreamRepository, times(1)).save(Mockito.<AnimalStream>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#update(AnimalUpdateDTO)}
     */
    @Test
    void testUpdate2() {
        // Arrange
        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");
        Optional<AnimalStream> ofResult = Optional.of(animalStream);
        when(animalStreamRepository.save(Mockito.<AnimalStream>any()))
                .thenThrow(new RuntimeException("Animal updated with id={}"));
        when(animalStreamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl.update(new AnimalUpdateDTO()));
        verify(animalStreamRepository).findById(Mockito.<Long>any());
        verify(animalStreamRepository, times(1)).save(Mockito.<AnimalStream>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#update(AnimalUpdateDTO)}
     */
    @Test
    void testUpdate3() {
        // Arrange
        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);
        AnimalStream animalStream = mock(AnimalStream.class);
        when(animalStream.getId()).thenReturn(1L);
        doNothing().when(animalStream).setAge(Mockito.<String>any());
        doNothing().when(animalStream).setErrorMessage(Mockito.<String>any());
        doNothing().when(animalStream).setKindAnimal(Mockito.<String>any());
        doNothing().when(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        doNothing().when(animalStream).setName(Mockito.<String>any());
        doNothing().when(animalStream).setProcessed(Mockito.<Boolean>any());
        doNothing().when(animalStream).setTypePowerSupply(Mockito.<String>any());
        doNothing().when(animalStream).setVenomous(Mockito.<String>any());
        doNothing().when(animalStream).setCreateDate(Mockito.<Timestamp>any());
        doNothing().when(animalStream).setId(Mockito.<Long>any());
        doNothing().when(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");
        Optional<AnimalStream> ofResult = Optional.of(animalStream);

        AnimalStreamLoadResult loadResult2 = new AnimalStreamLoadResult();
        loadResult2.setAnimalStreams(new ArrayList<>());
        loadResult2.setCreateDate(mock(Timestamp.class));
        loadResult2.setFilename("foo.txt");
        loadResult2.setId(1L);
        loadResult2.setLastUpdateDate(mock(Timestamp.class));
        loadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult2.setProcessed(true);
        loadResult2.setS3Link("S3 Link");
        loadResult2.setToDelete(true);

        AnimalStream animalStream2 = new AnimalStream();
        animalStream2.setAge("Age");
        animalStream2.setCreateDate(mock(Timestamp.class));
        animalStream2.setErrorMessage("An error occurred");
        animalStream2.setId(1L);
        animalStream2.setKindAnimal("Kind Animal");
        animalStream2.setLastUpdateDate(mock(Timestamp.class));
        animalStream2.setLoadResult(loadResult2);
        animalStream2.setName("Name");
        animalStream2.setProcessed(true);
        animalStream2.setTypePowerSupply("Type Power Supply");
        animalStream2.setVenomous("Venomous");
        when(animalStreamRepository.save(Mockito.<AnimalStream>any())).thenReturn(animalStream2);
        when(animalStreamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        animalStreamServiceImpl.update(new AnimalUpdateDTO());

        // Assert that nothing has changed
        verify(animalStream, atLeast(1)).setAge(Mockito.<String>any());
        verify(animalStream).setErrorMessage(eq("An error occurred"));
        verify(animalStream).setKindAnimal(eq("Kind Animal"));
        verify(animalStream).setLoadResult(Mockito.<AnimalStreamLoadResult>any());
        verify(animalStream, atLeast(1)).setName(Mockito.<String>any());
        verify(animalStream).setProcessed(Mockito.<Boolean>any());
        verify(animalStream, atLeast(1)).setTypePowerSupply(Mockito.<String>any());
        verify(animalStream, atLeast(1)).setVenomous(Mockito.<String>any());
        verify(animalStream).getId();
        verify(animalStream).setCreateDate(Mockito.<Timestamp>any());
        verify(animalStream).setId(Mockito.<Long>any());
        verify(animalStream).setLastUpdateDate(Mockito.<Timestamp>any());
        verify(animalStreamRepository).findById(Mockito.<Long>any());
        verify(animalStreamRepository, times(1)).save(Mockito.<AnimalStream>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#delete(AnimalDeleteDTO)}
     */
    @Test
    void testDelete() {
        // Arrange
        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");
        Optional<AnimalStream> ofResult = Optional.of(animalStream);
        doNothing().when(animalStreamRepository).delete(Mockito.<AnimalStream>any());
        when(animalStreamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        animalStreamServiceImpl.delete(new AnimalDeleteDTO());

        // Assert that nothing has changed
        verify(animalStreamRepository).delete(Mockito.<AnimalStream>any());
        verify(animalStreamRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#delete(AnimalDeleteDTO)}
     */
    @Test
    void testDelete2() {
        // Arrange
        AnimalStreamLoadResult loadResult = new AnimalStreamLoadResult();
        loadResult.setAnimalStreams(new ArrayList<>());
        loadResult.setCreateDate(mock(Timestamp.class));
        loadResult.setFilename("foo.txt");
        loadResult.setId(1L);
        loadResult.setLastUpdateDate(mock(Timestamp.class));
        loadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        loadResult.setProcessed(true);
        loadResult.setS3Link("S3 Link");
        loadResult.setToDelete(true);

        AnimalStream animalStream = new AnimalStream();
        animalStream.setAge("Age");
        animalStream.setCreateDate(mock(Timestamp.class));
        animalStream.setErrorMessage("An error occurred");
        animalStream.setId(1L);
        animalStream.setKindAnimal("Kind Animal");
        animalStream.setLastUpdateDate(mock(Timestamp.class));
        animalStream.setLoadResult(loadResult);
        animalStream.setName("Name");
        animalStream.setProcessed(true);
        animalStream.setTypePowerSupply("Type Power Supply");
        animalStream.setVenomous("Venomous");
        Optional<AnimalStream> ofResult = Optional.of(animalStream);
        doThrow(new RuntimeException("Animal in kafka deleted with id={}")).when(animalStreamRepository)
                .delete(Mockito.<AnimalStream>any());
        when(animalStreamRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl.delete(new AnimalDeleteDTO()));
        verify(animalStreamRepository).delete(Mockito.<AnimalStream>any());
        verify(animalStreamRepository, times(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#delete(AnimalDeleteDTO)}
     */
    @Test
    void testDelete3() {
        // Arrange
        Optional<AnimalStream> emptyResult = Optional.empty();
        when(animalStreamRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl.delete(new AnimalDeleteDTO()));
        verify(animalStreamRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#markErrorProcessed(Long)}
     */
    @Test
    void testMarkErrorProcessed() {
        // Arrange
        AnimalStreamLoadResult animalStreamLoadResult = new AnimalStreamLoadResult();
        animalStreamLoadResult.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult.setFilename("foo.txt");
        animalStreamLoadResult.setId(1L);
        animalStreamLoadResult.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult.setProcessed(true);
        animalStreamLoadResult.setS3Link("S3 Link");
        animalStreamLoadResult.setToDelete(true);
        Optional<AnimalStreamLoadResult> ofResult = Optional.of(animalStreamLoadResult);

        AnimalStreamLoadResult animalStreamLoadResult2 = new AnimalStreamLoadResult();
        animalStreamLoadResult2.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult2.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setFilename("foo.txt");
        animalStreamLoadResult2.setId(1L);
        animalStreamLoadResult2.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult2.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult2.setProcessed(true);
        animalStreamLoadResult2.setS3Link("S3 Link");
        animalStreamLoadResult2.setToDelete(true);
        when(animalStreamLoadResultRepository.save(Mockito.<AnimalStreamLoadResult>any()))
                .thenReturn(animalStreamLoadResult2);
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        animalStreamServiceImpl.markErrorProcessed(1L);

        // Assert
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
        verify(animalStreamLoadResultRepository, times(1)).save(Mockito.<AnimalStreamLoadResult>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#markErrorProcessed(Long)}
     */
    @Test
    void testMarkErrorProcessed2() {
        // Arrange
        AnimalStreamLoadResult animalStreamLoadResult = new AnimalStreamLoadResult();
        animalStreamLoadResult.setAnimalStreams(new ArrayList<>());
        animalStreamLoadResult.setCreateDate(mock(Timestamp.class));
        animalStreamLoadResult.setFilename("foo.txt");
        animalStreamLoadResult.setId(1L);
        animalStreamLoadResult.setLastUpdateDate(mock(Timestamp.class));
        animalStreamLoadResult.setProcessType(AnimalStreamProcessType.KAFKA);
        animalStreamLoadResult.setProcessed(true);
        animalStreamLoadResult.setS3Link("S3 Link");
        animalStreamLoadResult.setToDelete(true);
        Optional<AnimalStreamLoadResult> ofResult = Optional.of(animalStreamLoadResult);
        when(animalStreamLoadResultRepository.save(Mockito.<AnimalStreamLoadResult>any()))
                .thenThrow(new RuntimeException("foo"));
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> animalStreamServiceImpl.markErrorProcessed(1L));
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
        verify(animalStreamLoadResultRepository, times(1)).save(Mockito.<AnimalStreamLoadResult>any());
    }

    /**
     * Method under test: {@link AnimalStreamServiceImpl#markErrorProcessed(Long)}
     */
    @Test
    void testMarkErrorProcessed3() {
        // Arrange
        Optional<AnimalStreamLoadResult> emptyResult = Optional.empty();
        when(animalStreamLoadResultRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        animalStreamServiceImpl.markErrorProcessed(1L);

        // Assert that nothing has changed
        verify(animalStreamLoadResultRepository).findById(Mockito.<Long>any());
    }
}
