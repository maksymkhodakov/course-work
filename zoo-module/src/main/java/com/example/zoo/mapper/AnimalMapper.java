package com.example.zoo.mapper;

import com.example.zoo.dto.AnimalDTO;
import com.example.zoo.entity.Animal;
import org.springframework.stereotype.Component;


@Component
public class AnimalMapper {

    public AnimalDTO entityToDto(final Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .kindAnimal(animal.getKindAnimal())
                .typePowerSupply(animal.getTypePowerSupply())
                .venomous(animal.isVenomous())
                .photo(new byte[0])
                .build();
    }
}
