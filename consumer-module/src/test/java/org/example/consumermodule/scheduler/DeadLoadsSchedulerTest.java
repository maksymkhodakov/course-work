package org.example.consumermodule.scheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DeadLoadsScheduler.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DeadLoadsSchedulerTest {
    @MockBean
    private AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @Autowired
    private DeadLoadsScheduler deadLoadsScheduler;

    /**
     * Method under test: {@link DeadLoadsScheduler#processAnimalStream()}
     */
    @Test
    void testProcessAnimalStream() {
        // Arrange
        final List<AnimalStreamLoadResult> results = List.of(new AnimalStreamLoadResult());
        when(animalStreamLoadResultRepository.getAnimalStreamLoadResultByToDeleteIsTrue()).thenReturn(results);
        doNothing().when(animalStreamLoadResultRepository).deleteAll(any());

        // Act
        deadLoadsScheduler.processAnimalStream();

        // Assert that nothing has changed
        verify(animalStreamLoadResultRepository, times(1)).getAnimalStreamLoadResultByToDeleteIsTrue();
        verify(animalStreamLoadResultRepository, times(1)).deleteAll(results);
    }
}
