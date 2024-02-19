package org.example.consumermodule.kafka.listeners;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.services.FailureStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalUpdateDTO;
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
public class AnimalStreamApiUpdateConsumer {
    private final AnimalStreamService animalStreamService;
    private final FailureStreamService failureStreamService;

    @Transactional
    @KafkaListener(topics = "${topics.dev-update}", groupId = "${spring.kafka.consumer.group-id}")
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public void update(@Payload AnimalUpdateDTO animalUpdateDTO) {
        animalStreamService.update(animalUpdateDTO);
    }

    @DltHandler
    public void processUpdateError(AnimalUpdateDTO animalUpdateDTO) {
        log.info("Kafka got an ERROR object: {} during API update", animalUpdateDTO);
        failureStreamService.saveUnprocessed(AnimalStreamProcessType.KAFKA, AnimalStream.builder()
                .kindAnimal(animalUpdateDTO.getKindAnimal())
                .age(animalUpdateDTO.getAge())
                .name(animalUpdateDTO.getName())
                .typePowerSupply(animalUpdateDTO.getTypePowerSupply())
                .venomous(animalUpdateDTO.getVenomous())
                .errorMessage("Save animal update id=" + animalUpdateDTO.getId())
                .build());
    }
}
