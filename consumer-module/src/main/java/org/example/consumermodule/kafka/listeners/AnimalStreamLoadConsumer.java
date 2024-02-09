package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.retrytopic.DltStrategy.NO_DLT;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalStreamLoadConsumer {
    @Value("${kafka.topics.animal-stream-dlt}")
    private String dltTopic;
    private final KafkaSenderService kafkaSenderService;
    private final AnimalStreamService animalStreamService;

    @KafkaListener(topics = "${kafka.topics.animal-stream}", groupId = "${spring.kafka.consumer.group-id}")
    @RetryableTopic(
            attempts = "1",
            autoCreateTopics = "false",
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 5000),
            dltStrategy = NO_DLT,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void handleAnimalStream(@Payload AnimalStreamDTO animalStreamDTO) {
        try {
            log.info("Kafka got an object: {}", animalStreamDTO);
            animalStreamService.saveLoad(animalStreamDTO);
        } catch (Exception e) {
            kafkaSenderService.produceMessages(dltTopic, animalStreamDTO);
        }
    }

    @KafkaListener(topics = "${kafka.topics.animal-stream-dlt}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleAnimalStreamKafkaDlt(@Payload AnimalStreamDTO animalStreamDTO) {
        log.info("Kafka got an ERROR object: {}", animalStreamDTO);
        animalStreamService.markErrorProcessed(animalStreamDTO.getLoadId());
    }
}
