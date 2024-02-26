package org.example.consumermodule.rabbitmq.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalStreamApiListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamApiListenerTest {
    @Autowired
    private AnimalStreamApiListener animalStreamApiListener;

    @MockBean
    private AnimalStreamService animalStreamService;

    /**
     * Method under test: {@link AnimalStreamApiListener#save(AnimalDTO)}
     */
    @Test
    void testSave() {
        // Arrange
        doNothing().when(animalStreamService).save(any(), any());
        AnimalDTO animalDTO = new AnimalDTO("Name", "Kind Animal", "Venomous", "Type Power Supply", "Age");

        // Act
        animalStreamApiListener.save(animalDTO);

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).save(any(), any());
        assertEquals("Age", animalDTO.getAge());
        assertEquals("Kind Animal", animalDTO.getKindAnimal());
        assertEquals("Name", animalDTO.getName());
        assertEquals("Type Power Supply", animalDTO.getTypePowerSupply());
        assertEquals("Venomous", animalDTO.getVenomous());
    }

    /**
     * Method under test: {@link AnimalStreamApiListener#update(AnimalUpdateDTO)}
     */
    @Test
    void testUpdate() {
        // Arrange
        doNothing().when(animalStreamService).update(any());

        // Act
        animalStreamApiListener.update(new AnimalUpdateDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).update(any());
    }

    /**
     * Method under test: {@link AnimalStreamApiListener#delete(AnimalDeleteDTO)}
     */
    @Test
    void testDelete() {
        // Arrange
        doNothing().when(animalStreamService).delete(any());

        // Act
        animalStreamApiListener.delete(new AnimalDeleteDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).delete(any());
    }
}
