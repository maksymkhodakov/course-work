package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.domain.dto.AnimalDTO;
import org.example.producermodule.domain.dto.AnimalDeleteDTO;
import org.example.producermodule.domain.dto.AnimalUpdateDTO;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.kafka.retrytopic.DltStrategy.FAIL_ON_ERROR;


@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topics.dev}", groupId = "${spring.kafka.consumer.group-id}")
public class KafkaConsumer {
    private final AnimalStreamService animalStreamService;

    @Transactional
    @KafkaHandler
    public void save(@Payload AnimalDTO animalDTO) {
        animalStreamService.save(animalDTO);
    }

    @Transactional
    @KafkaHandler
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = FAIL_ON_ERROR,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void update(@Payload AnimalUpdateDTO animalUpdateDTO) {
        animalStreamService.update(animalUpdateDTO);
    }

    @Transactional
    @KafkaHandler
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = FAIL_ON_ERROR,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void delete(@Payload AnimalDeleteDTO animalDeleteDTO) {
        animalStreamService.delete(animalDeleteDTO);
    }
}
