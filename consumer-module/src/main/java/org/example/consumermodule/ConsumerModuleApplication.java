package org.example.consumermodule;

import com.example.zoo.ZooApplication;
import org.example.producermodule.ProducerModuleApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import({ProducerModuleApplication.class, ZooApplication.class})
@EnableScheduling
@SpringBootApplication
public class ConsumerModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerModuleApplication.class, args);
	}

}
