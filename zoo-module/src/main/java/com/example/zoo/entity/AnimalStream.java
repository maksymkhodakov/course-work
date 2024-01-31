package com.example.zoo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animal_stream")
public class AnimalStream extends TimestampEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "kind_animal")
    private String kindAnimal;

    @Column(name = "venomous")
    private String venomous;

    @Column(name = "type_power_supply")
    private String typePowerSupply;

    @Column(name = "age")
    private String age;
}
