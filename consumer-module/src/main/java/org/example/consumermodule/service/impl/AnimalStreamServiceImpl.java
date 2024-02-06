package org.example.consumermodule.service.impl;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.AnimalStream;
import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.enums.KindAnimal;
import com.example.zoo.enums.TypePowerSupply;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import com.example.zoo.repository.AnimalStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalStreamServiceImpl implements AnimalStreamService {
    private final AnimalRepository animalRepository;
    private final AnimalStreamRepository animalStreamRepository;
    private final AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @Override
    @Transactional
    public void processAnimalStreams(List<AnimalStream> animalStreams) {
        if (Objects.isNull(animalStreams) || animalStreams.isEmpty()) {
            return;
        }

        final List<Animal> animals = new ArrayList<>();

        animalStreams.forEach(animalStream -> {
            validateAnimalStream(animalStream);
            if (animalStream.getErrorMessage() != null && !animalStream.getErrorMessage().isEmpty()) {
                animalStream.setProcessed(false);
            } else {
                final Animal animalTransformed = buildAnimalFromStreamData(animalStream);
                animals.add(animalTransformed);
                // keep historical data
                animalStream.setProcessed(true);
            }
        });

        animalRepository.saveAll(animals);
        animalStreamRepository.saveAll(animalStreams);
    }

    private void validateAnimalStream(AnimalStream animalStream) {
        final List<String> errors = new ArrayList<>();

        validateName(animalStream, errors);
        validateKind(animalStream, errors);
        validateVenomous(animalStream, errors);
        validatePowerSupply(animalStream, errors);
        validateAge(animalStream, errors);

        animalStream.setErrorMessage(String.join(",", errors));
    }

    private void validateAge(AnimalStream animalStream, List<String> errors) {
        if (animalStream.getAge() == null || animalStream.getAge().isEmpty()) {
            errors.add("Invalid age");
        } else {
            try {
                Integer.parseInt(animalStream.getAge());
            } catch(Exception e) {
                errors.add("Invalid age");
            }
        }
    }

    private void validatePowerSupply(AnimalStream animalStream, List<String> errors) {
        if (animalStream.getTypePowerSupply() == null || animalStream.getTypePowerSupply().isEmpty() || !isValidTypePowerSupplyEnumValue(animalStream)) {
            errors.add("Invalid type power supply");
        } else {
            animalStream.setTypePowerSupply(animalStream.getTypePowerSupply().trim());
        }
    }

    private void validateVenomous(AnimalStream animalStream, List<String> errors) {
        if (animalStream.getVenomous() == null || animalStream.getVenomous().isEmpty() ||
                (!animalStream.getVenomous().equalsIgnoreCase("true") &&
                        !animalStream.getVenomous().equalsIgnoreCase("false"))) {
            errors.add("Invalid venomous");
        } else {
            animalStream.setVenomous(animalStream.getVenomous().trim());
        }
    }

    private void validateKind(AnimalStream animalStream, List<String> errors) {
        if (animalStream.getKindAnimal() == null || animalStream.getKindAnimal().isEmpty() || !isValidKindEnumValue(animalStream)) {
            errors.add("Invalid kind");
        } else {
            animalStream.setKindAnimal(animalStream.getKindAnimal().trim());
        }
    }

    private void validateName(AnimalStream animalStream, List<String> errors) {
        if (animalStream.getName() == null || animalStream.getName().isEmpty()) {
            errors.add("Invalid name");
        } else {
            animalStream.setName(animalStream.getName().trim());
        }
    }

    private Animal buildAnimalFromStreamData(AnimalStream animalStream) {
        return Animal.builder()
                .name(animalStream.getName())
                .kindAnimal(KindAnimal.valueOf(animalStream.getKindAnimal()))
                .venomous(Boolean.parseBoolean(animalStream.getVenomous()))
                .typePowerSupply(TypePowerSupply.valueOf(animalStream.getTypePowerSupply()))
                .countries(new ArrayList<>())
                .build();
    }

    private boolean isValidTypePowerSupplyEnumValue(AnimalStream animalStream) {
        return Arrays.stream(TypePowerSupply.values())
                .map(TypePowerSupply::name)
                .toList()
                .contains(animalStream.getTypePowerSupply());
    }

    private boolean isValidKindEnumValue(AnimalStream animalStream) {
        return Arrays.stream(KindAnimal.values())
                .map(KindAnimal::name)
                .toList()
                .contains(animalStream.getKindAnimal());
    }

    @Override
    @Transactional
    public void saveLoad(AnimalStreamDTO animalStreamDTO) {
        final AnimalStreamLoadResult animalStreamLoadResult = animalStreamLoadResultRepository.findById(animalStreamDTO.getLoadId())
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_LOAD_NOT_FOUND));

        final List<AnimalStream> animalStreams = animalStreamDTO.getAnimalStream()
                .stream()
                .map(this::buildAnimalStream)
                .toList();

        animalStreams.forEach(animalStreamLoadResult::addAnimalStream);
        animalStreamLoadResult.setProcessed(true);

        animalStreamLoadResultRepository.save(animalStreamLoadResult);
    }

    @Override
    @Transactional
    public void save(AnimalDTO animalDTO) {
        log.info("Consumer received message value: {}", animalDTO);
        final AnimalStream animalStream = buildAnimalStream(animalDTO);
        final AnimalStream savedAnimal = animalStreamRepository.save(animalStream);
        log.info("Animal with id={} was saved to DB",savedAnimal.getId());
    }

    private AnimalStream buildAnimalStream(AnimalDTO animalDTO) {
        return AnimalStream.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.getVenomous())
                .typePowerSupply(animalDTO.getTypePowerSupply())
                .kindAnimal(animalDTO.getKindAnimal())
                .age(animalDTO.getAge())
                .build();
    }

    private AnimalStream buildAnimalStream(AnimalStreamFileDTO animalDTO) {
        return AnimalStream.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.getVenomous())
                .typePowerSupply(animalDTO.getTypePowerSupply())
                .kindAnimal(animalDTO.getKindAnimal())
                .age(animalDTO.getAge())
                .build();
    }

    @Override
    @Transactional
    public void update(AnimalUpdateDTO animalUpdateDTO) {
        final AnimalStream animalStream = animalStreamRepository.findById(animalUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal in DB not found"));

        animalStream.setName(animalUpdateDTO.getName());
        animalStream.setAge(animalUpdateDTO.getAge());
        animalStream.setVenomous(animalUpdateDTO.getVenomous());
        animalStream.setTypePowerSupply(animalUpdateDTO.getTypePowerSupply());
        animalStream.setAge(animalUpdateDTO.getAge());

        animalStreamRepository.save(animalStream);
        log.info("Animal updated with id={}", animalStream.getId());
    }

    @Override
    @Transactional
    public void delete(AnimalDeleteDTO animalDeleteDTO) {
        final AnimalStream animalStream = animalStreamRepository.findById(animalDeleteDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal in kafka not found"));
        animalStreamRepository.delete(animalStream);
        log.info("Animal in kafka deleted with id={}", animalDeleteDTO.getId());
    }
}
