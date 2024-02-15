package org.example.consumermodule.scheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.repository.AnimalStreamRepository;

import java.util.List;

import org.example.consumermodule.service.AnimalStreamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalScheduler.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalSchedulerTest {
    @Autowired
    private AnimalScheduler animalScheduler;

    @MockBean
    private AnimalStreamRepository animalStreamRepository;

    @MockBean
    private AnimalStreamService animalStreamService;

    /**
     * Method under test: {@link AnimalScheduler#processAnimalStream()}
     */
    @Test
    void testProcessAnimalStream() {
        // Arrange
        final AnimalStream animalStream = new AnimalStream();
        doNothing().when(animalStreamService).processAnimalStreams(any());
        when(animalStreamRepository.findByProcessedIsNull()).thenReturn(List.of(animalStream));

        // Act
        animalScheduler.processAnimalStream();

        // Assert that nothing has changed
        verify(animalStreamRepository, times(1)).findByProcessedIsNull();
        verify(animalStreamService, times(1)).processAnimalStreams(List.of(animalStream));
    }
}
