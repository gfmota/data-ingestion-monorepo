package evolvability.thesis.austrian_geosphere_data_collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class DataCollectorTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCollectorTemplateApplication.class, args);
	}

}
