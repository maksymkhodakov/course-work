package org.example.producermodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ProducerModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerModuleApplication.class, args);
	}

}
