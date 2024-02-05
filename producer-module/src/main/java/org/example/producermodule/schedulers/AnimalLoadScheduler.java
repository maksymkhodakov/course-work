package org.example.producermodule.schedulers;

import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.service.AnimalLoadProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalLoadScheduler {
    private final AnimalLoadProcessor processor;
    private final AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @Scheduled(cron = "${animal.load.cron}")
    public void animalLoad() {
        log.info("Start executing animal load cron job");

        final List<AnimalStreamLoadResult> loadsToProcess = animalStreamLoadResultRepository.getAnimalStreamLoadResultByProcessedIsFalse();

        log.info("Found {} loads to process", loadsToProcess.size());

        loadsToProcess.forEach(processor::process);

        log.info("Finish executing animal load cron job");
    }
}
