package evolvability.thesis.orchestrator.consumers;

import evolvability.thesis.orchestrator.dtos.DataTransformationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataTransformationConsumer {

    @RabbitListener(queues = "${data-consumer.queue}")
    private void consume(final DataTransformationMessage message) {
        log.info("Received message: {}", message);
    }
}
