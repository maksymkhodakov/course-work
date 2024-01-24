package org.example.producermodule.rabbitmq.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.rabbitmq.domain.dto.CreateAnimalDTO;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RabbitMqController {

    private final MessageProducerWrapper messageProducerWrapper;

    @PostMapping("/rabbitmq/producer-dev/produce")
    public ResponseEntity<Void> produce(@RequestBody @Valid CreateAnimalDTO createAnimalDTO) {
        messageProducerWrapper.produceMessages(createAnimalDTO);
        return ResponseEntity.ok().build();
    }

}
