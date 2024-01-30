package org.example.producermodule.kafka.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSenderServiceImpl implements KafkaSenderService {
    @Value("${kafka.topics.dev}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @SneakyThrows
    @Override
    public void produceMessages(Object object) {
        final CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, object);
        future.whenComplete((result, ex) -> {
            if (Objects.isNull(ex)) {
                log.info("Sent message=[" + object + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" + object + "] due to : " + ex.getMessage());
            }
        });
        log.info("Kafka message send, message={}", object);
    }
}
