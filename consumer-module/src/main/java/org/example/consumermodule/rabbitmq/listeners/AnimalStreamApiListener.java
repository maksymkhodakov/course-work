package org.example.consumermodule.rabbitmq.listeners;

import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.global.GlobalConstants;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = {GlobalConstants.DEV_QUEUE_NAME})
public class AnimalStreamApiListener {
    private final AnimalStreamService animalStreamService;

    @RabbitHandler
    @Transactional
    public void save(AnimalDTO animalDTO) {
        animalStreamService.save(animalDTO);
    }

    @RabbitHandler
    @Transactional
    public void update(AnimalUpdateDTO animalUpdateDTO) {
        animalStreamService.update(animalUpdateDTO);
    }

    @RabbitHandler
    @Transactional
    public void delete(AnimalDeleteDTO animalDeleteDTO) {
        animalStreamService.delete(animalDeleteDTO);
    }
}
