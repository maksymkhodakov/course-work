package org.example.producermodule.rabbitmq.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnimalDTO {
    @NotNull
    private String name;
    @NotNull
    private int age;
    @NotNull
    private boolean venomous;
}
