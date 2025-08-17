package ryzendee.app.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ryzendee.app.common.dto.ContractorUpdateEvent;
import ryzendee.app.common.exception.MappingException;
import ryzendee.app.model.Outbox;

@Component
@RequiredArgsConstructor
public class ContractorUpdateEventPublisher implements EventPublisher {

    @Value("${rabbit.contractor.exchange}")
    private String contractorExchange;

    @Value("${rabbit.contractor.routing-key}")
    private String contractorUpdatedRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Outbox outbox) {
        ContractorUpdateEvent event = toEvent(outbox.getPayload());
        rabbitTemplate.convertAndSend(
                contractorExchange,
                contractorUpdatedRoutingKey,
                event
        );
    }

    private ContractorUpdateEvent toEvent(String payload) {
        try {
            return objectMapper.readValue(payload, ContractorUpdateEvent.class);
        } catch (JsonProcessingException ex) {
            throw new MappingException("Failed to deserialize", ex);
        }
    }
}
