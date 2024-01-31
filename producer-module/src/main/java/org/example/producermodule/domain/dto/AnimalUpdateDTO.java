package org.example.producermodule.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String kindAnimal;

    @NotNull
    private String venomous;

    @NotNull
    private String typePowerSupply;

    @NotNull
    private String age;
}
