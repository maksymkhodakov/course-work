package org.example.producermodule.kafka.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSenderServiceImpl implements KafkaSenderService {
    private final ObjectMapper objectMapper;

    @Value("${kafka.topics.dev}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @Override
    public void produceMessages(Object object) {
        final String message = objectMapper.writeValueAsString(object);
        final CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            if (Objects.isNull(ex)) {
                log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
        log.info("Kafka message send, message={}", object);
    }
}
