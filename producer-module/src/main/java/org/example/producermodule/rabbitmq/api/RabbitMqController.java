package org.example.producermodule.rabbitmq.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.domain.dto.MessageWrapper;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RabbitMqController {
    // REFACTOR!!!!
    @Value("${rabbit.handler.exchange}")
    private String exchangeName;

    @Value("${rabbit.handler.routing_key}")
    private String routingKeyName;

    private final MessageProducerWrapper messageProducerWrapper;

    @PostMapping("/rabbit/produce")
    public ResponseEntity<Void> produce() {
        final var message = new MessageWrapper<Integer>(List.of(1, 2, 3), "no text");
        messageProducerWrapper.produceMessages(exchangeName, routingKeyName, message);
        return ResponseEntity.ok().build();
    }

    @RabbitListener(queues = "dev")
    public void listenDev(final MessageWrapper<Integer> messageWrapper) {
        log.info(messageWrapper.getData().toString());
    }

}
