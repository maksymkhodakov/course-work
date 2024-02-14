package org.example.consumermodule.rabbitmq.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.rabbitmq.global.GlobalConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(queues = {GlobalConstants.DLQ_NAME})
@RequiredArgsConstructor
public class DLQListener {
    private final AnimalStreamService animalStreamService;

    @RabbitHandler
    public void handleAnimalStreamError(@Payload AnimalStreamDTO animalStreamDTO) {
        log.info("Rabbit mq got an ERROR object: {}", animalStreamDTO);
        animalStreamService.markErrorProcessed(animalStreamDTO.getLoadId());
    }

    @RabbitHandler
    public void handleAnimalSaveError(@Payload AnimalDTO animalDTO) {
        log.info("Rabbit mq got an ERROR object: {}, during save", animalDTO);
    }

    @RabbitHandler
    public void handleAnimalUpdateError(@Payload AnimalUpdateDTO animalUpdateDTO) {
        log.info("Rabbit mq got an ERROR object: {}, during update", animalUpdateDTO);
    }

    @RabbitHandler
    public void handleAnimalDeleteError(@Payload AnimalDeleteDTO animalDeleteDTO) {
        log.info("Rabbit mq got an ERROR object: {}, during delete", animalDeleteDTO);
    }
}
