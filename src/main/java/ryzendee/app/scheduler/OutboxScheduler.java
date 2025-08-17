package ryzendee.app.scheduler;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.common.enums.ProcessingStatus;
import ryzendee.app.model.Outbox;
import ryzendee.app.publisher.EventPublisher;
import ryzendee.app.repository.OutboxRepository;

import java.util.List;

/**
 * Планировщик для обработки сообщений в outbox.
 * <p>
 * Периодически выбирает все события со статусом PENDING из таблицы {@link Outbox},
 * публикует их через {@link EventPublisher} и обновляет статус обработки.
 * <ul>
 *     <li>{@link ProcessingStatus#PROCESSED} — если событие успешно отправлено</li>
 *     <li>{@link ProcessingStatus#FAILED} — если при отправке произошла ошибка</li>
 * </ul>
 * <p>
 * Использует механизм блокировок {@link SchedulerLock}, чтобы обеспечить
 * единовременную обработку сообщений только одним инстансом приложения.
 *
 * @author Dmitry Ryazantsev
 */
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final EventPublisher eventPublisher;
    private final OutboxRepository outboxRepository;

    @Value("${outbox.scheduler.batch-size:50}")
    private int batchSize;

    /**
     * Обрабатывает все необработанные сообщения из outbox:
     * отправляет их через {@link EventPublisher} и обновляет статус.
     */
    @Transactional
    @Scheduled(
            fixedDelayString = "${outbox.scheduler.fixed-delay-ms:5000}",
            initialDelayString = "${outbox.scheduler.initial-delay-ms:1000}"
    )
    @SchedulerLock(
            name = "processOutboxMessages",
            lockAtMostFor = "PT5M",
            lockAtLeastFor = "PT0S"
    )
    public void process() {
        List<Outbox> outboxes = outboxRepository.findAllPendingSortByCreatedDate(batchSize);

        outboxes.forEach(outbox -> {
            try {
                eventPublisher.send(outbox);
                outbox.setProcessingStatus(ProcessingStatus.PROCESSED);
            } catch (Exception ex) {
                outbox.setProcessingStatus(ProcessingStatus.FAILED);
            }
        });

        outboxRepository.updateBatch(outboxes);
    }
}
