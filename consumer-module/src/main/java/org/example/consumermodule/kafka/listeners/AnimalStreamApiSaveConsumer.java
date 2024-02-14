package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDTO;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalStreamApiSaveConsumer {
    private final AnimalStreamService animalStreamService;

    @Transactional
    @KafkaListener(topics = "${topics.dev-save}", groupId = "${spring.kafka.consumer.group-id}")
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public void save(@Payload AnimalDTO animalDTO) {
        animalStreamService.save(animalDTO);
    }

    @DltHandler
    public void processSaveError(AnimalDTO animalDTO) {
        log.info("Kafka got an ERROR object: {} during API save", animalDTO);
    }
}
