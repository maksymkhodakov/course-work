package org.example.consumermodule.scheduler;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.repository.AnimalStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnimalScheduler {
    private final AnimalStreamService animalStreamService;
    private final AnimalStreamRepository animalStreamRepository;

    @Scheduled(cron = "${animal.stream.cron}")
    public void processAnimalStream() {
        log.info("Start processing of unprocessed animal streams");

        final List<AnimalStream> animalStreams = animalStreamRepository.findByProcessedIsNull();

        log.info("Found {} animal streams to process", animalStreams.size());

        animalStreamService.processAnimalStreams(animalStreams);

        log.info("Finish processing of unprocessed animal streams");
    }
}
