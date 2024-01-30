package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.domain.entity.AnimalStream;
import org.example.consumermodule.domain.repositories.AnimalRepository;
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
    private final AnimalRepository animalRepository;

    @Transactional
    @KafkaHandler
    public void save(@Payload AnimalDTO animalDTO) {
        log.info("Consumer received message value: {}", animalDTO);
        final AnimalStream animalStream = AnimalStream.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.isVenomous())
                .build();

        final AnimalStream savedAnimal = animalRepository.save(animalStream);
        log.info("Animal with id={} was saved to DB",savedAnimal.getId());
    }

    @Transactional
    @KafkaHandler
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = FAIL_ON_ERROR,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void update(@Payload AnimalUpdateDTO animalUpdateDTO) {
        final AnimalStream animalStream = animalRepository.findById(animalUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal in Kafka not found"));

        animalStream.setName(animalUpdateDTO.getName());
        animalStream.setAge(animalUpdateDTO.getAge());
        animalStream.setVenomous(animalUpdateDTO.isVenomous());

        animalRepository.save(animalStream);
        log.info("Animal updated with id={}", animalStream.getId());
    }

    @Transactional
    @KafkaHandler
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = FAIL_ON_ERROR,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void delete(@Payload AnimalDeleteDTO animalDeleteDTO) {
        final AnimalStream animalStream = animalRepository.findById(animalDeleteDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal in kafka not found"));
        animalRepository.delete(animalStream);
        log.info("Animal in kafka deleted with id={}", animalDeleteDTO.getId());
    }


}
