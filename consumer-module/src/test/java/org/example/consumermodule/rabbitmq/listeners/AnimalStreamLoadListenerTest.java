package org.example.consumermodule.rabbitmq.listeners;

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

@ContextConfiguration(classes = {AnimalStreamLoadListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamLoadListenerTest {
    @Autowired
    private AnimalStreamLoadListener animalStreamLoadListener;

    @MockBean
    private AnimalStreamService animalStreamService;

    /**
     * Method under test:
     * {@link AnimalStreamLoadListener#handleAnimalStreamDlt(AnimalStreamDTO)}
     */
    @Test
    void testHandleAnimalStreamDlt() {
        // Arrange
        doNothing().when(animalStreamService).saveLoad(any());

        // Act
        animalStreamLoadListener.handleAnimalStreamDlt(new AnimalStreamDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).saveLoad(any());
    }
}
