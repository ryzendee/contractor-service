package ryzendee.app.model;

import lombok.*;
import ryzendee.app.common.enums.EventType;
import ryzendee.app.common.enums.ProcessingStatus;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Сущность outbox-сообщения.
 *
 * Используется в реализации outbox-паттерна для гарантированной доставки сообщений
 *
 * @author Dmitry Ryazantsev
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Outbox {

    private Long id;
    private EventType eventType;
    private String payload;

    @Builder.Default
    private ProcessingStatus processingStatus = ProcessingStatus.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
