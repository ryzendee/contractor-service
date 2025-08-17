package ryzendee.app.repository;

import ryzendee.app.model.Outbox;

import java.util.List;

/**
 * Интерфейс базового репозитория для работы с Outbox-сообщениями {@link Outbox}.
 *
 * @author Dmitry Ryazantsev
 */
public interface OutboxRepository {

    /**
     * Сохраняет новое Outbox-сообщение или обновляет существующее.
     *
     * @param outbox объект сообщения для сохранения или обновления
     * @return сохранённый или обновлённый объект сообщения
     */
    Outbox save(Outbox outbox);

    /**
     * Находит список Outbox-сообщений, которые ещё не были обработаны.
     * Используется для выборки сообщений, которые требуют отправки или обработки.
     *
     * @param limit максимальное количество сообщений, возвращаемых за один вызов
     * @return список сообщений с заданным типом и статусом PENDING
     */
    List<Outbox> findAllPendingSortByCreatedDate(int limit);

    /**
     * Выполняет batch-обновление Outbox-сообщений по всем полям. Id берется из сущностей.
     *
     * @param outboxes обновленные сущности
     */
    void updateBatch(List<Outbox> outboxes);
}
