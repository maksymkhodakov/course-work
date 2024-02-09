package org.example.consumermodule.rabbitmq.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalStreamDTO;
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
    public void errorAnimalDeleteQueue(Long id) {
        log.info("Hi from error queue! Animal with id={}, not exists in DB", id);
    }

    @RabbitHandler
    public void handleAnimalStream(@Payload AnimalStreamDTO animalStreamDTO) {
        log.info("Rabbit mq got an ERROR object: {}", animalStreamDTO);
        animalStreamService.markErrorProcessed(animalStreamDTO.getLoadId());
    }
}
