package org.example.producermodule.kafka.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.domain.dto.AnimalDTO;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KafkaController {
    private final KafkaSenderService kafkaSenderService;

    @PostMapping("/kafka/produce/animal/save")
    public ResponseEntity<Void> produceCreate(@RequestBody @Valid AnimalDTO animalDTO) {
        kafkaSenderService.produceMessages(animalDTO);
        return ResponseEntity.ok().build();
    }
}
