package com.example.zoo.services.impl;

import com.example.zoo.dto.AnimalStreamResultDTO;
import com.example.zoo.entity.AnimalStream;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.repository.AnimalStreamRepository;
import com.example.zoo.services.FailureStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FailureStreamServiceImpl implements FailureStreamService {
    private final AnimalStreamRepository animalStreamRepository;


    @Override
    @Transactional
    public void saveUnprocessed(AnimalStream animalStream) {
        animalStream.setProcessed(false);
        animalStreamRepository.save(animalStream);
    }

    @Override
    public List<AnimalStreamResultDTO> getUnprocessed() {
        return animalStreamRepository.findByProcessedIsFalse()
                .stream()
                .map(this::mapToResultDTO)
                .sorted(Comparator.comparing(AnimalStreamResultDTO::getUploadedDate, Comparator.reverseOrder()))
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final AnimalStream animalStream = animalStreamRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_STREAM_NOT_FOUND));
        animalStreamRepository.delete(animalStream);
    }

    private AnimalStreamResultDTO mapToResultDTO(AnimalStream animalStream) {
        return AnimalStreamResultDTO.builder()
                .id(animalStream.getId())
                .name(animalStream.getName())
                .kindAnimal(animalStream.getKindAnimal())
                .typePowerSupply(animalStream.getTypePowerSupply())
                .age(animalStream.getAge())
                .venomous(animalStream.getVenomous())
                .uploadedDate(animalStream.getCreateDate())
                .errorMessage(animalStream.getErrorMessage())
                .filename(Objects.isNull(animalStream.getLoadResult()) ? null : animalStream.getLoadResult().getFilename())
                .build();
    }
}
