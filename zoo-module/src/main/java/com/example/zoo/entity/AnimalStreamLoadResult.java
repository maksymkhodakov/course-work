package com.example.zoo.entity;


import com.example.zoo.enums.AnimalStreamProcessType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Table(name = "animal_load_result")
public class AnimalStreamLoadResult extends TimestampEntity {

    @Column(name = "process_type")
    @Enumerated(EnumType.STRING)
    private AnimalStreamProcessType processType;

    @Column(name = "filename")
    private String filename;

    @Column(name = "s3_link")
    private String s3Link;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loadResult", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<AnimalStream> animalStream = new ArrayList<>();
}
