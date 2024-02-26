package com.example.zoo.entity;


import com.example.zoo.enums.KindAnimal;
import com.example.zoo.enums.TypePowerSupply;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Table(name = "animal")
public class Animal extends TimestampEntity {

    private String name;

    // field which can be populated only by a streams
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private KindAnimal kindAnimal;

    private boolean venomous;

    @Enumerated(value = EnumType.STRING)
    private TypePowerSupply typePowerSupply;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<Country> countries = new ArrayList<>();

    /**
     * For storing photos for animals AWS S3 is used
     */
    @Column(name = "photo_path")
    private String photoPath;

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void removeCountry(Country country) {
        if (!countries.isEmpty()) {
            countries.remove(country);
        }
    }
}
