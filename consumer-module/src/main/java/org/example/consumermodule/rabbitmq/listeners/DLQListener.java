package org.example.consumermodule.rabbitmq.listeners;

import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.global.GlobalConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(queues = {GlobalConstants.DLQ_NAME})
public class DLQListener {
    @RabbitHandler
    public void errorAnimalDeleteQueue(Long id) {
        log.info("Hi from error queue! Animal with id={}, not exists in DB", id);
    }
}
