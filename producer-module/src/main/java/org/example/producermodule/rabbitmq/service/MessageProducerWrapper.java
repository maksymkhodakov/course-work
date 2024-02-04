package org.example.producermodule.rabbitmq.service;

public interface MessageProducerWrapper {
    void produceDevMessages(final Object object);
    void produceAnimalStreamMessages(final Object object);
}
