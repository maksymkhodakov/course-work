package org.example.consumermodule.scheduler;

import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeadLoadsScheduler {
    private final AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @Transactional
    @Scheduled(cron = "${animal.dead.load.cron}")
    public void processAnimalStream() {
        log.info("Start processing of dead animal loads");

        final List<AnimalStreamLoadResult> deadLoads = animalStreamLoadResultRepository.getAnimalStreamLoadResultByToDeleteIsTrue();

        log.info("Found {} loads to delete", deadLoads.size());

        animalStreamLoadResultRepository.deleteAll(deadLoads);

        log.info("Finish processing of dead animal loads");
    }
}
