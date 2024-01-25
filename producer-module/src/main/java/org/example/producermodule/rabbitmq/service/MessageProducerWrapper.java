package org.example.producermodule.rabbitmq.service;


public interface MessageProducerWrapper {
    void produceMessages(final Object objects);
}
