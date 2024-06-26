package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalStreamLoadConsumer {
    private final AnimalStreamService animalStreamService;

    @KafkaListener(topics = "${topics.animal-stream}", groupId = "${spring.kafka.consumer.group-id}")
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public void handleAnimalStream(@Payload AnimalStreamDTO animalStreamDTO) {
        log.info("Kafka got an object: {}", animalStreamDTO);
        animalStreamService.saveLoad(animalStreamDTO);
    }

    @DltHandler
    public void processMessage(AnimalStreamDTO animalStreamDTO) {
        log.info("Kafka got an ERROR object: {}, on mass stream upload", animalStreamDTO);
        animalStreamService.markErrorProcessed(animalStreamDTO.getLoadId());
    }
}
