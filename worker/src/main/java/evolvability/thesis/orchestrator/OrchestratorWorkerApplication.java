package evolvability.thesis.orchestrator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class OrchestratorWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorWorkerApplication.class, args);
	}

}
