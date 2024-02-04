package org.example.producermodule.service.impl;

import com.example.zoo.entity.AnimalStreamLoadResult;
import lombok.RequiredArgsConstructor;
import org.example.producermodule.service.AnimalLoadProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalLoadProcessorImpl implements AnimalLoadProcessor {

    @Override
    public void process(AnimalStreamLoadResult load) {
        // TODO implement
    }
}
