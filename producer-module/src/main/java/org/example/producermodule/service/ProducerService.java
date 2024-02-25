package org.example.producermodule.service;

import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.enums.KindAnimal;
import com.example.zoo.enums.TypePowerSupply;

public interface ProducerService {
    void handleSave(AnimalStreamProcessType processType, Integer age, String name, KindAnimal kindAnimal, boolean venomous, TypePowerSupply typePowerSupply);
    void handleUpdate(AnimalStreamProcessType processType, Integer id, Integer age, String name, KindAnimal kindAnimal, boolean venomous, TypePowerSupply typePowerSupply);
    void handleDelete(AnimalStreamProcessType processType, Integer id);
}
