package org.example.consumermodule.kafka.listeners;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalStreamApiDeleteConsumer.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamApiDeleteConsumerTest {
    @Autowired
    private AnimalStreamApiDeleteConsumer animalStreamApiDeleteConsumer;

    @MockBean
    private AnimalStreamService animalStreamService;

    /**
     * Method under test:
     * {@link AnimalStreamApiDeleteConsumer#delete(AnimalDeleteDTO)}
     */
    @Test
    void testDelete() {
        // Arrange
        doNothing().when(animalStreamService).delete(any());

        // Act
        animalStreamApiDeleteConsumer.delete(new AnimalDeleteDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).delete(any());
    }

    /**
     * Method under test:
     * {@link AnimalStreamApiDeleteConsumer#processDeleteError(AnimalDeleteDTO)}
     */
    @Test
    void testProcessDeleteError() {
        // Arrange
        AnimalDeleteDTO animalDeleteDTO = new AnimalDeleteDTO();

        // Act
        animalStreamApiDeleteConsumer.processDeleteError(animalDeleteDTO);

        // Assert that nothing has changed
        assertNull(animalDeleteDTO.getId());
    }
}
