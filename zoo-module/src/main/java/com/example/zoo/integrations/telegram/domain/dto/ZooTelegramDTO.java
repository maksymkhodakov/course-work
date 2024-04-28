package com.example.zoo.integrations.telegram.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZooTelegramDTO {
    private String name;
    private String countryName;
    private double square;
    private List<String> animalNames;
    private List<byte[]> photos;
}
