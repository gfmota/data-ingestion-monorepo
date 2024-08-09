package evolvability.thesis.ingest_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class IngestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestServiceApplication.class, args);
	}

}
