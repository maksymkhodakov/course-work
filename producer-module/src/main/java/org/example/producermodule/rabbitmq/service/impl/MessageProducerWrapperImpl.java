package org.example.producermodule.rabbitmq.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.global.GlobalConstants;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerWrapperImpl implements MessageProducerWrapper {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produceMessages(final Object object) {
        log.info("Start to produce messages");
        rabbitTemplate.convertAndSend(
                GlobalConstants.DEV_EXCHANGE_NAME,
                GlobalConstants.DEV_ROUTING_KEY,
                object);
        log.info("Finished to produce messages");
    }
}
