package org.example.producermodule.schedulers;

import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;

import java.util.ArrayList;
import java.util.List;

import org.example.producermodule.service.AnimalLoadProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AnimalLoadScheduler.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalLoadSchedulerTest {
    @MockBean
    private AnimalLoadProcessor animalLoadProcessor;

    @Autowired
    private AnimalLoadScheduler animalLoadScheduler;

    @MockBean
    private AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    /**
     * Method under test: {@link AnimalLoadScheduler#animalLoad()}
     */
    @Test
    void testAnimalLoad() {
        // Arrange
        final List<AnimalStreamLoadResult> load = List.of(AnimalStreamLoadResult.builder().build());
        when(animalStreamLoadResultRepository.getAnimalStreamLoadResultByProcessedIsFalse()).thenReturn(load);

        // Act
        animalLoadScheduler.animalLoad();

        // Assert that nothing has changed
        verify(animalStreamLoadResultRepository, times(1)).getAnimalStreamLoadResultByProcessedIsFalse();
        verify(animalLoadProcessor, times(1)).process(load.get(0));
    }
}
