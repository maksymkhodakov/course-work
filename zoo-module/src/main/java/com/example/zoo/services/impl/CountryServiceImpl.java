package com.example.zoo.services.impl;

import com.example.zoo.data.CountryData;
import com.example.zoo.dto.CountryDTO;
import com.example.zoo.dto.SearchDTO;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.mapper.CountryMapper;
import com.example.zoo.repository.CountryRepository;
import com.example.zoo.services.CountryService;
import com.example.zoo.utils.SearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
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
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    @Cacheable(value = COUNTRIES, cacheManager = "cacheManager")
    public List<CountryDTO> getAll() {
        return countryRepository.findAll()
                .stream()
                .map(CountryMapper::entityToDto)
                .toList();
    }

    @Override
    public Page<CountryDTO> getAll(SearchDTO searchDTO) {
        return countryRepository.findAll(SearchUtil.getPageable(searchDTO))
                .map(CountryMapper::entityToDto);
    }

    @Override
    @Transactional
    @CachePut(value = COUNTRIES)
    public void save(CountryData countryData, MultipartFile multipartFile) throws IOException {
        final var country = CountryMapper.dataToEntity(countryData, multipartFile.getBytes());
        countryRepository.saveAndFlush(country);
        log.info("Country with id: " + country.getId() + " created");
    }

    @Override
    @Transactional
    public void update(Long id, CountryData countryData, MultipartFile multipartFile) throws IOException {
        final var country = countryRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.COUNTRY_NOT_FOUND));
        country.setName(countryData.getName());
        country.setContinent(countryData.getContinent());
        country.setFlag(multipartFile.getBytes());
    }

    @Override
    @Cacheable(value = COUNTRIES, cacheManager = "cacheManager")
    public CountryDTO getById(Long id) {
        return CountryMapper.entityToDto(countryRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.COUNTRY_NOT_FOUND)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final var country = countryRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.COUNTRY_NOT_FOUND));
        countryRepository.delete(country);
    }
}
