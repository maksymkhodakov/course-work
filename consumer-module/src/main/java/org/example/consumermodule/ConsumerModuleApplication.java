package org.example.consumermodule;

import org.example.producermodule.ProducerModuleApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@Import(ProducerModuleApplication.class)
@EnableKafka
@SpringBootApplication
public class ConsumerModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerModuleApplication.class, args);
	}

}
