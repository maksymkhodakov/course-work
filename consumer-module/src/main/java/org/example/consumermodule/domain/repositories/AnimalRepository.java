package org.example.consumermodule.domain.repositories;

import org.example.consumermodule.domain.entity.AnimalStream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalStream, Long> {
}
