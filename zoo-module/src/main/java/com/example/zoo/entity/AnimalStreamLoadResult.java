package com.example.zoo.entity;


import com.example.zoo.enums.AnimalStreamProcessType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "is_processed")
    private boolean processed;

    @Column(name = "is_to_delete")
    private Boolean toDelete;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loadResult", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<AnimalStream> animalStreams = new ArrayList<>();

    public void addAnimalStream(AnimalStream animalStream) {
        if (Objects.nonNull(animalStream)) {
            this.animalStreams.add(animalStream);
            animalStream.setLoadResult(this);
        }
    }
}
