package ryzendee.app.service.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.common.api.Event;
import ryzendee.app.common.exception.MappingException;
import ryzendee.app.model.Outbox;
import ryzendee.app.repository.OutboxRepository;

/**
 * Вспомогательный компонент для сохранения событий во временное хранилище (outbox).
 *
 * Сериализует событие в JSON и сохраняет его в таблицу {@link Outbox},
 * откуда оно может быть опубликовано во внешнюю систему через {@code EventPublisher}.
 *
 * @author Dmitry Ryazantsev
 */
@Component
@RequiredArgsConstructor
public class OutboxHelper {

    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    /**
     * Сохраняет событие в outbox.
     *
     * @param event событие {@link Event}, которое необходимо сохранить
     * @throws MappingException если не удалось сериализовать объект события
     */
    @Transactional
    public void save(Event event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            Outbox outbox = Outbox.builder()
                    .eventType(event.getEventType())
                    .payload(payload)
                    .build();
            outboxRepository.save(outbox);
        } catch (JsonProcessingException ex) {
            throw new MappingException("Failed to create outbox", ex);
        }
    }
}
