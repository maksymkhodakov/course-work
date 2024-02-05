package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.retrytopic.DltStrategy.FAIL_ON_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topics.animal-stream}", groupId = "${spring.kafka.consumer.group-id}")
public class AnimalStreamLoadConsumer {
    private final AnimalStreamService animalStreamService;

    @KafkaHandler
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = FAIL_ON_ERROR,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void handleAnimalStream(@Payload AnimalStreamDTO animalStreamDTO) {
        log.info("Kafka got an object: {}", animalStreamDTO);
        animalStreamService.saveLoad(animalStreamDTO);
    }
}
