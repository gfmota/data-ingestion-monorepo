package evolvability.thesis.ingestcore;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableRabbit
@EnableFeignClients(basePackages = "evolvability.thesis.clients")
public class IngestCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestCoreApplication.class, args);
	}

}
