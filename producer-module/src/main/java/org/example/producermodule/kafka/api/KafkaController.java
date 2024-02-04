package org.example.producermodule.kafka.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KafkaController {
    @Value("${kafka.topics.dev}")
    private String topic;
    private final KafkaSenderService kafkaSenderService;

    @PostMapping("/kafka/produce/animal/save")
    public ResponseEntity<Void> produceCreate(@RequestBody @Valid AnimalDTO animalDTO) {
        kafkaSenderService.produceMessages(topic, animalDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/kafka/produce/animal/update")
    public ResponseEntity<Void> produceUpdate(@RequestBody @Valid AnimalUpdateDTO animalUpdateDTO) {
        kafkaSenderService.produceMessages(topic, animalUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/kafka/produce/animal/delete")
    public ResponseEntity<Void> produceDelete(@RequestBody @Valid AnimalDeleteDTO animalDeleteDTO) {
        kafkaSenderService.produceMessages(topic, animalDeleteDTO);
        return ResponseEntity.ok().build();
    }
}
