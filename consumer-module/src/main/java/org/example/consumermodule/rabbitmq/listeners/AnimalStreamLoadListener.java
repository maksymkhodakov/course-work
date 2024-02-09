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
@RequiredArgsConstructor
@RabbitListener(queues = {GlobalConstants.ANIMAl_QUEUE_NAME})
public class AnimalStreamLoadListener {
    private final AnimalStreamService animalStreamService;

    @RabbitHandler
    public void handleAnimalStreamDlt(@Payload AnimalStreamDTO animalStreamDTO) {
        log.info("Rabbit mq got an object: {}", animalStreamDTO);
        animalStreamService.saveLoad(animalStreamDTO);
    }
}
