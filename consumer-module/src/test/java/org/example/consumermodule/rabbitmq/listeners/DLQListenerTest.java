package org.example.consumermodule.rabbitmq.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.zoo.services.FailureStreamService;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DLQListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DLQListenerTest {
    @MockBean
    private AnimalStreamService animalStreamService;

    @MockBean
    private FailureStreamService failureStreamService;

    @Autowired
    private DLQListener dLQListener;

    /**
     * Method under test:
     * {@link DLQListener#handleAnimalStreamError(AnimalStreamDTO)}
     */
    @Test
    void testHandleAnimalStreamError() {
        // Arrange
        doNothing().when(animalStreamService).markErrorProcessed(any());

        // Act
        dLQListener.handleAnimalStreamError(new AnimalStreamDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).markErrorProcessed(any());
    }

    /**
     * Method under test: {@link DLQListener#handleAnimalSaveError(AnimalDTO)}
     */
    @Test
    void testHandleAnimalSaveError() {
        // Arrange
        AnimalDTO animalDTO = new AnimalDTO("Name", "Kind Animal", "Venomous", "Type Power Supply", "Age");

        // Act
        dLQListener.handleAnimalSaveError(animalDTO);

        // Assert that nothing has changed
        assertEquals("Age", animalDTO.getAge());
        assertEquals("Kind Animal", animalDTO.getKindAnimal());
        assertEquals("Name", animalDTO.getName());
        assertEquals("Type Power Supply", animalDTO.getTypePowerSupply());
        assertEquals("Venomous", animalDTO.getVenomous());
    }

    /**
     * Method under test:
     * {@link DLQListener#handleAnimalUpdateError(AnimalUpdateDTO)}
     */
    @Test
    void testHandleAnimalUpdateError() {
        // Arrange
        AnimalUpdateDTO animalUpdateDTO = new AnimalUpdateDTO();

        // Act
        dLQListener.handleAnimalUpdateError(animalUpdateDTO);

        // Assert that nothing has changed
        assertNull(animalUpdateDTO.getId());
        assertNull(animalUpdateDTO.getAge());
        assertNull(animalUpdateDTO.getKindAnimal());
        assertNull(animalUpdateDTO.getName());
        assertNull(animalUpdateDTO.getTypePowerSupply());
        assertNull(animalUpdateDTO.getVenomous());
    }

    /**
     * Method under test:
     * {@link DLQListener#handleAnimalDeleteError(AnimalDeleteDTO)}
     */
    @Test
    void testHandleAnimalDeleteError() {
        // Arrange
        AnimalDeleteDTO animalDeleteDTO = new AnimalDeleteDTO();

        // Act
        dLQListener.handleAnimalDeleteError(animalDeleteDTO);

        // Assert that nothing has changed
        assertNull(animalDeleteDTO.getId());
    }
}
