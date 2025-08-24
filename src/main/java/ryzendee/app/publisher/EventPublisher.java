package ryzendee.app.publisher;

import ryzendee.app.model.Outbox;

/**
 * Публикатор событий.
 *
 * Отвечает за отправку сообщений используя {@link Outbox}
 *
 * @author Dmitry Ryazantsev
 */
public interface EventPublisher {

    /**
     * Отправляет событие во внешнюю систему.
     *
     * @param outbox объект {@link Outbox}, содержащий данные события
     */
    void send(Outbox outbox);
}
