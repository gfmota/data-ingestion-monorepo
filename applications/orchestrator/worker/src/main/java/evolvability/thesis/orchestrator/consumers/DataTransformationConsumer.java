package evolvability.thesis.orchestrator.consumers;

import evolvability.thesis.orchestrator.dtos.DataTransformationMessage;
import evolvability.thesis.orchestrator.process.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataTransformationConsumer {
    private final ProcessService processService;

    @RabbitListener(queues = "${data-consumer.queue}")
    private void consume(final DataTransformationMessage message) throws Exception {
        processService.processMessage(message.rawData(), message.metadata());
    }

}
