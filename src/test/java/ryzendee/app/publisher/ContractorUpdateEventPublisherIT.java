package ryzendee.app.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ryzendee.app.common.dto.ContractorUpdateEvent;
import ryzendee.app.model.Outbox;
import ryzendee.app.testutils.testcontainers.EnableTestcontainers;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@EnableTestcontainers
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = {
                ContractorUpdateEventPublisher.class,
                RabbitAutoConfiguration.class,
                RabbitPublisherTestConfig.class
        }
)
public class ContractorUpdateEventPublisherIT {

    private static final int TIMEOUT_MS = 5000;

    @Autowired
    private ContractorUpdateEventPublisher contractorUpdateEventPublisher;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void send_shouldSend() throws JsonProcessingException {
        ContractorUpdateEvent event = new ContractorUpdateEvent("id", "inn", "name", now());
        Outbox outbox = createOutbox(event);

        contractorUpdateEventPublisher.send(outbox);

        Object messageObj = rabbitTemplate.receiveAndConvert(RabbitPublisherTestConfig.TEST_QUEUE, TIMEOUT_MS);
        assertThat(messageObj).isInstanceOf(ContractorUpdateEvent.class);

        ContractorUpdateEvent message = (ContractorUpdateEvent) messageObj;
        assertThat(message).isNotNull();
        assertThat(message).isEqualTo(event);
    }

    private Outbox createOutbox(ContractorUpdateEvent event) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(event);

        return Outbox.builder()
                .eventType(event.getEventType())
                .payload(json)
                .build();
    }
}
