package com.example.zoo.services.impl;

import com.example.zoo.storage.config.AWSProperties;
import com.example.zoo.storage.service.StorageService;
import com.example.zoo.utils.SearchUtil;
import com.example.zoo.data.AnimalData;
import com.example.zoo.dto.AnimalDTO;
import com.example.zoo.dto.CountryDTO;
import com.example.zoo.dto.SearchDTO;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.mapper.AnimalMapper;
import com.example.zoo.mapper.CountryMapper;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.repository.CountryRepository;
import com.example.zoo.services.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.zoo.config.CachingConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final CountryRepository countryRepository;
    private final AnimalMapper animalMapper;
    private final StorageService storageService;
    private final AWSProperties awsProperties;

    @Override
    @Cacheable(value = ANIMALS, cacheManager = "cacheManager")
    public List<AnimalDTO> getAll() {
        return animalRepository.findAll()
                .stream()
                .map(animalMapper::entityToDto)
                .toList();
    }

    @Override
    public Page<AnimalDTO> getAll(SearchDTO searchDTO) {
        return animalRepository.findAll(SearchUtil.getPageable(searchDTO))
                .map(animalMapper::entityToDto);
    }

    @Override
    @Transactional
    public void update(Long id, AnimalData animalData, MultipartFile multipartFile) throws IOException {
        final var animalToUpdate = animalRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND));
        animalToUpdate.setName(animalData.getName());
        animalToUpdate.setVenomous(animalData.isVenomous());
        animalToUpdate.setTypePowerSupply(animalData.getTypePowerSupply());
        animalToUpdate.setKindAnimal(animalData.getKindAnimal());
        animalToUpdate.setPhotoPath(storageService.updateFile(awsProperties.getZooServiceBucketName(), animalToUpdate.getPhotoPath(), multipartFile));
    }

    @Override
    @Cacheable(value = ANIMALS, cacheManager = "cacheManager")
    public AnimalDTO getById(Long id) {
        return animalMapper.entityToDto(animalRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final var animalToDelete = animalRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND));
        animalRepository.delete(animalToDelete);
    }

    @Override
    public List<CountryDTO> getRelatedCountries(Long id) {
        var animal = animalRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND));
        return animal.getCountries()
                .stream()
                .map(CountryMapper::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCountry(Long countryId, Long id) {
        var animal = animalRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND));

        var country = countryRepository.findById(countryId)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND));

        animal.removeCountry(country);
    }

    @Override
    @Transactional
    public void addCountry(Long animalId, Long countryId) {
        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_NOT_FOUND));

        var country = countryRepository.findById(countryId)
                .orElseThrow(() -> new OperationException(ApiErrors.COUNTRY_NOT_FOUND));
        animal.addCountry(country);
    }
}
