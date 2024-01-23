package org.example.producermodule.rabbitmq.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerWrapperImpl implements MessageProducerWrapper {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public <T> void produceMessages(final String queueName, final String routingKey, final T object) {
        log.info("Start to produce messages");
        rabbitTemplate.convertAndSend(queueName, routingKey, object);
        log.info("Finished to produce messages");
    }
}
