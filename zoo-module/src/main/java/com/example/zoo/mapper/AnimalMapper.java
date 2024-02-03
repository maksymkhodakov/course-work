package com.example.zoo.mapper;

import com.example.zoo.dto.AnimalDTO;
import com.example.zoo.entity.Animal;
import com.example.zoo.storage.config.AWSProperties;
import com.example.zoo.storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AnimalMapper {
    private final AWSProperties awsProperties;
    private final S3Service s3Service;

    public AnimalDTO entityToDto(final Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .kindAnimal(animal.getKindAnimal())
                .typePowerSupply(animal.getTypePowerSupply())
                .venomous(animal.isVenomous())
                .photo(s3Service.downloadFile(awsProperties.getZooServiceBucketName(), animal.getPhotoPath()))
                .build();
    }
}
