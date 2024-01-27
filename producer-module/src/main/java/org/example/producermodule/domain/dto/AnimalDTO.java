package org.example.producermodule.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO implements Serializable {
    @NotNull
    private String name;
    @NotNull
    private int age;
    @NotNull
    private boolean venomous;
}
