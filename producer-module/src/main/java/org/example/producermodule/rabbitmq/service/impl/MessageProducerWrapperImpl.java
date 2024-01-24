package org.example.producermodule.rabbitmq.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerWrapperImpl implements MessageProducerWrapper {
    @Value("${rabbit.handler.exchange}")
    private String exchangeName;

    @Value("${rabbit.handler.routing_key}")
    private String routingKeyName;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produceMessages(final Object object) {
        log.info("Start to produce messages");
        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, object);
        log.info("Finished to produce messages");
    }
}
