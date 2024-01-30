package org.example.consumermodule.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @Transactional
    @KafkaListener(topics = "${kafka.topics.dev}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> message) {
        log.info("Consumer received message for topic: {}, value: {}", message.topic(), message.value());
    }
}
