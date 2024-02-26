package org.example.consumermodule.kafka.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.zoo.services.FailureStreamService;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalStreamApiSaveConsumer.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamApiSaveConsumerTest {
    @Autowired
    private AnimalStreamApiSaveConsumer animalStreamApiSaveConsumer;

    @MockBean
    private AnimalStreamService animalStreamService;

    @MockBean
    private FailureStreamService failureStreamService;

    /**
     * Method under test: {@link AnimalStreamApiSaveConsumer#save(AnimalDTO)}
     */
    @Test
    void testSave() {
        // Arrange
        doNothing().when(animalStreamService).save(any(), any());
        AnimalDTO animalDTO = new AnimalDTO("Name", "Kind Animal", "Venomous", "Type Power Supply", "Age");

        // Act
        animalStreamApiSaveConsumer.save(animalDTO);

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).save(any(), any());
        assertEquals("Age", animalDTO.getAge());
        assertEquals("Kind Animal", animalDTO.getKindAnimal());
        assertEquals("Name", animalDTO.getName());
        assertEquals("Type Power Supply", animalDTO.getTypePowerSupply());
        assertEquals("Venomous", animalDTO.getVenomous());
    }

    /**
     * Method under test:
     * {@link AnimalStreamApiSaveConsumer#processSaveError(AnimalDTO)}
     */
    @Test
    void testProcessSaveError() {
        // Arrange
        AnimalDTO animalDTO = new AnimalDTO("Name", "Kind Animal", "Venomous", "Type Power Supply", "Age");

        // Act
        animalStreamApiSaveConsumer.processSaveError(animalDTO);

        // Assert that nothing has changed
        assertEquals("Age", animalDTO.getAge());
        assertEquals("Kind Animal", animalDTO.getKindAnimal());
        assertEquals("Name", animalDTO.getName());
        assertEquals("Type Power Supply", animalDTO.getTypePowerSupply());
        assertEquals("Venomous", animalDTO.getVenomous());
    }
}
