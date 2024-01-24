package org.example.producermodule.rabbitmq.service;


public interface MessageProducerWrapper {
    <T> void produceMessages(final T objects);
}
