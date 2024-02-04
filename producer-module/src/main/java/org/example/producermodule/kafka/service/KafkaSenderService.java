package org.example.producermodule.kafka.service;

public interface KafkaSenderService {
    void produceMessages(final String topic, final Object object);
}
