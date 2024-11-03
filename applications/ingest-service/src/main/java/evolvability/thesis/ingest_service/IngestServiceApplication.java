package evolvability.thesis.ingest_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableRabbit
@EnableFeignClients
public class IngestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestServiceApplication.class, args);
	}

}
