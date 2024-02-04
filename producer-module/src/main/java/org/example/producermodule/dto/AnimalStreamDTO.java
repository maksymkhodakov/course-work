package org.example.producermodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalStreamDTO implements Serializable {
    private Long loadId;
    private List<AnimalStreamFileDTO> animalStream;
}
