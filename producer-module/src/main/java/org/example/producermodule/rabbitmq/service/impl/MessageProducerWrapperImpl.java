package org.example.producermodule.rabbitmq.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.global.GlobalConstants;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerWrapperImpl implements MessageProducerWrapper {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produceDevMessages(final Object object) {
        log.info("Start to produce dev messages");
        rabbitTemplate.convertAndSend(GlobalConstants.DEV_EXCHANGE_NAME, GlobalConstants.DEV_ROUTING_KEY, object);
        log.info("Finished to produce dev messages, sent: {}", object);
    }

    @Override
    public void produceAnimalStreamMessages(Object object) {
        log.info("Start to produce animal stream messages");
        rabbitTemplate.convertAndSend(GlobalConstants.ANIMAL_EXCHANGE_NAME, GlobalConstants.ANIMAL_ROUTING_KEY, object);
        log.info("Finished to produce animal stream messages, sent: {}", object);
    }
}
