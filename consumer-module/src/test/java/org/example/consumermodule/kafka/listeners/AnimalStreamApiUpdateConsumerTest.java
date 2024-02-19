package org.example.consumermodule.kafka.listeners;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.zoo.services.FailureStreamService;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalStreamApiUpdateConsumer.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalStreamApiUpdateConsumerTest {
    @Autowired
    private AnimalStreamApiUpdateConsumer animalStreamApiUpdateConsumer;

    @MockBean
    private AnimalStreamService animalStreamService;

    @MockBean
    private FailureStreamService failureStreamService;

    /**
     * Method under test:
     * {@link AnimalStreamApiUpdateConsumer#update(AnimalUpdateDTO)}
     */
    @Test
    void testUpdate() {
        // Arrange
        doNothing().when(animalStreamService).update(any());

        // Act
        animalStreamApiUpdateConsumer.update(new AnimalUpdateDTO());

        // Assert that nothing has changed
        verify(animalStreamService, times(1)).update(any());
    }

    /**
     * Method under test:
     * {@link AnimalStreamApiUpdateConsumer#processUpdateError(AnimalUpdateDTO)}
     */
    @Test
    void testProcessUpdateError() {
        // Arrange
        AnimalUpdateDTO animalUpdateDTO = new AnimalUpdateDTO();

        // Act
        animalStreamApiUpdateConsumer.processUpdateError(animalUpdateDTO);

        // Assert that nothing has changed
        assertNull(animalUpdateDTO.getId());
        assertNull(animalUpdateDTO.getAge());
        assertNull(animalUpdateDTO.getKindAnimal());
        assertNull(animalUpdateDTO.getName());
        assertNull(animalUpdateDTO.getTypePowerSupply());
        assertNull(animalUpdateDTO.getVenomous());
    }
}
