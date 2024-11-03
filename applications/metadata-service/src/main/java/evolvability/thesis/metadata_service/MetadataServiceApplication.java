package evolvability.thesis.metadata_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MetadataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetadataServiceApplication.class, args);
	}

}
