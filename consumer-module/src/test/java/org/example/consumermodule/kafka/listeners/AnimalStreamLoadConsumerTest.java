package org.example.consumermodule.kafka.listeners;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalStreamLoadConsumer.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamLoadConsumerTest {
    @Autowired
    private AnimalStreamLoadConsumer animalStreamLoadConsumer;

    @MockBean
    private AnimalStreamService animalStreamService;

    /**
     * Method under test:
     * {@link AnimalStreamLoadConsumer#handleAnimalStream(AnimalStreamDTO)}
     */
    @Test
    void testHandleAnimalStream() {
        // Arrange
        doNothing().when(animalStreamService).saveLoad(any());

        // Act
        animalStreamLoadConsumer.handleAnimalStream(new AnimalStreamDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).saveLoad(any());
    }

    /**
     * Method under test:
     * {@link AnimalStreamLoadConsumer#processMessage(AnimalStreamDTO)}
     */
    @Test
    void testProcessMessage() {
        // Arrange
        doNothing().when(animalStreamService).markErrorProcessed(any());

        // Act
        animalStreamLoadConsumer.processMessage(new AnimalStreamDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).markErrorProcessed(any());
    }
}
