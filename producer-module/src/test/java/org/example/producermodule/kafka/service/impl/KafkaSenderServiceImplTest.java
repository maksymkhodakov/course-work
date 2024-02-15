package org.example.producermodule.kafka.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {KafkaSenderServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class KafkaSenderServiceImplTest {
    @Autowired
    private KafkaSenderServiceImpl kafkaSenderServiceImpl;

    @MockBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Method under test:
     * {@link KafkaSenderServiceImpl#produceMessages(String, Object)}
     */
    @Test
    void testProduceMessages() {
        // Arrange
        when(kafkaTemplate.send(any(), any())).thenReturn(new CompletableFuture<>());

        // Act
        kafkaSenderServiceImpl.produceMessages("Topic", "Object");

        // Assert that nothing has changed
        verify(kafkaTemplate).send(eq("Topic"), any());
    }

    /**
     * Method under test:
     * {@link KafkaSenderServiceImpl#produceMessages(String, Object)}
     */
    @Test
    void testProduceMessages2() {
        // Arrange
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>("Kafka message send, message={}", "Value");

        completableFuture.obtrudeValue(new SendResult<>(producerRecord,
                new RecordMetadata(new TopicPartition("Kafka message send, message={}", 1), 1L, 1, 10L, 3, 3)));
        when(kafkaTemplate.send(any(), any())).thenReturn(completableFuture);

        // Act
        kafkaSenderServiceImpl.produceMessages("Topic", "Object");

        // Assert that nothing has changed
        verify(kafkaTemplate).send(eq("Topic"), any());
    }

    /**
     * Method under test:
     * {@link KafkaSenderServiceImpl#produceMessages(String, Object)}
     */
    @Test
    void testProduceMessages3() {
        // Arrange
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeException(new Throwable());
        when(kafkaTemplate.send(any(), any())).thenReturn(completableFuture);

        // Act
        kafkaSenderServiceImpl.produceMessages("Topic", "Object");

        // Assert that nothing has changed
        verify(kafkaTemplate).send(eq("Topic"), any());
    }

    /**
     * Method under test:
     * {@link KafkaSenderServiceImpl#produceMessages(String, Object)}
     */
    @Test
    void testProduceMessages4() {
        // Arrange
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();
        completableFuture
                .obtrudeValue(new SendResult<>(new ProducerRecord<>("Kafka message send, message={}", "Value"), null));
        when(kafkaTemplate.send(any(), any())).thenReturn(completableFuture);

        // Act
        kafkaSenderServiceImpl.produceMessages("Topic", "Object");

        // Assert that nothing has changed
        verify(kafkaTemplate).send(eq("Topic"), any());
    }

    /**
     * Method under test:
     * {@link KafkaSenderServiceImpl#produceMessages(String, Object)}
     */
    @Test
    void testProduceMessages5() {
        // Arrange
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(null);
        when(kafkaTemplate.send(any(), any())).thenReturn(completableFuture);

        // Act
        kafkaSenderServiceImpl.produceMessages("Topic", "Object");

        // Assert that nothing has changed
        verify(kafkaTemplate).send(eq("Topic"), any());
    }
}
