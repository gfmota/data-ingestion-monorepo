package evolvability.thesis.austrian_geosphere_data_collector.infrastructure.publishers;

import evolvability.thesis.austrian_geosphere_data_collector.domain.entity.EnrichedData;
import evolvability.thesis.austrian_geosphere_data_collector.domain.gateways.PublisherGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMessagePublisher implements PublisherGateway {
    @Value("${rabbitmq.queue.routing-key}")
    private String rabbitMqQueueRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishData(EnrichedData data) {
        log.info("Publishing data {} to rabbit mq queue {}", data, rabbitMqQueueRoutingKey);
        rabbitTemplate.convertAndSend(rabbitMqQueueRoutingKey, data);
    }
}
