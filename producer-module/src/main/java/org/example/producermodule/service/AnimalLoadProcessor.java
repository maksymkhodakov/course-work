package org.example.producermodule.service;

import com.example.zoo.entity.AnimalStreamLoadResult;

public interface AnimalLoadProcessor {
    void process(AnimalStreamLoadResult load);
}
