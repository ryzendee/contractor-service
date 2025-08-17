package ryzendee.app.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ryzendee.app.common.enums.EventType;
import ryzendee.app.common.enums.ProcessingStatus;
import ryzendee.app.publisher.EventPublisher;
import ryzendee.app.model.Outbox;
import ryzendee.app.repository.OutboxRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OutboxSchedulerTest {

    @InjectMocks
    private OutboxScheduler outboxScheduler;

    @Mock
    private EventPublisher eventPublisher;
    @Mock
    private OutboxRepository outboxRepository;

    private List<Outbox> outboxList;
    private Outbox outbox;

    @BeforeEach
    void setUp() {
        outbox = Outbox.builder()
                .id(1L)
                .eventType(EventType.CONTRACTOR_UPDATE)
                .payload("{}")
                .processingStatus(ProcessingStatus.PENDING)
                .build();

        outboxList = new ArrayList<>();
        outboxList.add(outbox);
    }

    @Test
    void process_sendingSuccessfully_shouldMarkAsProcessed() {
        when(outboxRepository.findAllPendingSortByCreatedDate(anyInt())).thenReturn(outboxList);

        outboxScheduler.process();

        verify(outboxRepository).findAllPendingSortByCreatedDate(anyInt());
        verify(outboxRepository).updateBatch(outboxList);

        assertThat(outbox.getProcessingStatus()).isEqualTo(ProcessingStatus.PROCESSED);
    }

    @Test
    void process_sendingFailed_shouldMarkAsFailed() {
        when(outboxRepository.findAllPendingSortByCreatedDate(anyInt())).thenReturn(outboxList);
        doThrow(RuntimeException.class)
                .when(eventPublisher).send(outbox);

        outboxScheduler.process();

        verify(outboxRepository).findAllPendingSortByCreatedDate(anyInt());
        verify(outboxRepository).updateBatch(outboxList);

        assertThat(outbox.getProcessingStatus()).isEqualTo(ProcessingStatus.FAILED);
    }
}
