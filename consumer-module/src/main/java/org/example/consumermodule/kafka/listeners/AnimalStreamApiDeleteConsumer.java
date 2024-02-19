package org.example.consumermodule.kafka.listeners;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.services.FailureStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDeleteDTO;
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
public class AnimalStreamApiDeleteConsumer {
    private final AnimalStreamService animalStreamService;
    private final FailureStreamService failureStreamService;

    @Transactional
    @KafkaListener(topics = "${topics.dev-delete}", groupId = "${spring.kafka.consumer.group-id}")
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public void delete(@Payload AnimalDeleteDTO animalDeleteDTO) {
        animalStreamService.delete(animalDeleteDTO);
    }

    @DltHandler
    public void processDeleteError(AnimalDeleteDTO animalDeleteDTO) {
        log.info("Kafka got an ERROR object: {} during API delete", animalDeleteDTO);
        failureStreamService.saveUnprocessed(AnimalStream.builder()
                .errorMessage("Delete animal with id=" + animalDeleteDTO.getId())
                .build());
    }
}
