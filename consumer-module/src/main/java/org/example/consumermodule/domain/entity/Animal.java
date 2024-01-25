package org.example.consumermodule.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "animal")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Animal extends TimestampEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "venomous")
    private boolean venomous;
}
