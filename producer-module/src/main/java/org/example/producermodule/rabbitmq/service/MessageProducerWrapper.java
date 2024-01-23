package org.example.producermodule.rabbitmq.service;


public interface MessageProducerWrapper {
    <T> void produceMessages(final String queueName, final String routingKey, final T objects);
}
