package org.example.consumermodule;

import com.example.zoo.ZooLabApplication;
import org.example.producermodule.ProducerModuleApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({ProducerModuleApplication.class, ZooLabApplication.class})
@SpringBootApplication
public class ConsumerModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerModuleApplication.class, args);
	}

}
