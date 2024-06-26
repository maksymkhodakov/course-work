package com.example.zoo.entity;

import com.example.zoo.enums.AnimalStreamProcessType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "loadResult")
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

    @ManyToOne
    @JoinColumn(name = "animal_stream_load_result_id")
    private AnimalStreamLoadResult loadResult;

    @Column(name = "is_processed")
    private Boolean processed;

    @Column(name = "error_msg")
    private String errorMessage;

    // setup on create
    @Column(name = "process_type")
    @Enumerated(EnumType.STRING)
    private AnimalStreamProcessType processType;
}
