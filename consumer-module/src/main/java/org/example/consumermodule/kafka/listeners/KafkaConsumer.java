package org.example.consumermodule.kafka.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.consumermodule.domain.entity.AnimalStream;
import org.example.consumermodule.domain.repositories.AnimalRepository;
import org.example.producermodule.domain.dto.AnimalDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ObjectMapper objectMapper;
    private final AnimalRepository animalRepository;

    @Transactional
    @KafkaListener(topics = "${kafka.topics.dev}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> message) {
        log.info("Consumer received message for topic: {}", message.topic());
        readMessage(objectMapper.convertValue(message.value(), AnimalDTO.class));
    }


    public void readMessage(AnimalDTO message) {
        final AnimalStream animalStream = AnimalStream.builder()
                .name(message.getName())
                .age(message.getAge())
                .venomous(message.isVenomous())
                .build();
        final AnimalStream savedAnimalStream = animalRepository.save(animalStream);

        log.info("Animal saved with id={}", savedAnimalStream.getId());
    }
}
