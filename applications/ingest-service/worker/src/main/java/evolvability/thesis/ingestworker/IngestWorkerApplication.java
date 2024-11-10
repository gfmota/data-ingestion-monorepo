package evolvability.thesis.ingestworker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableRabbit
@ComponentScan(basePackages = {"evolvability.thesis.ingestcore", "evolvability.thesis.ingestworker"})
public class IngestWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestWorkerApplication.class, args);
	}

}
