package org.example.producermodule;

import com.example.zoo.ZooApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@Import(ZooApplication.class)
public class ProducerModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerModuleApplication.class, args);
	}

}
