package ryzendee.app.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ryzendee.app.common.enums.ProcessingStatus;
import ryzendee.app.mapper.OutboxRowMapper;
import ryzendee.app.model.Outbox;
import ryzendee.app.repository.OutboxRepository;

import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

    private static final String SQL_INSERT = """
            INSERT INTO outbox 
                (event_type, payload, outbox_status, processed_at)
            VALUES 
                (:eventType, :payload::jsonb, :outboxStatus, :processedAt)
            """;

    private static final String SQL_UPDATE = """
            UPDATE outbox
            SET event_type = :eventType,
                payload = :payload::jsonb,
                outbox_status = :outboxStatus,
                processed_at = :processedAt
            WHERE id = :id
            """;

    private static final String SQL_FIND_BY_STATUS_SORTED = """
            SELECT * 
            FROM outbox 
            WHERE outbox_status = :status
            ORDER BY created_at
            LIMIT :limit
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OutboxRowMapper outboxRowMapper;

    @Override
    public Outbox save(Outbox outbox) {
        if (outbox.getId() == null) {
            Number generatedId = insert(outbox);
            outbox.setId(generatedId.longValue());
        } else {
            update(outbox);
        }
        return outbox;
    }

    @Override
    public List<Outbox> findAllPendingSortByCreatedDate(int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("status", ProcessingStatus.PENDING.name())
                .addValue("limit", limit);

        return jdbcTemplate.query(SQL_FIND_BY_STATUS_SORTED, params, outboxRowMapper);
    }

    @Override
    public void updateBatch(List<Outbox> outboxes) {
        MapSqlParameterSource[] batchParams = outboxes.stream()
                .map(this::getParamsWithId)
                .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(SQL_UPDATE, batchParams);
    }

    private Number insert(Outbox outbox) {
        outbox.setProcessedAt(now());
        MapSqlParameterSource params = getParams(outbox);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_INSERT, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey();
    }

    private void update(Outbox outbox) {
        MapSqlParameterSource params = getParams(outbox);
        params.addValue("id", outbox.getId());
        jdbcTemplate.update(SQL_UPDATE, params);
    }

    private MapSqlParameterSource getParamsWithId(Outbox message) {
        MapSqlParameterSource params = getParams(message);
        params.addValue("id", message.getId());
        return params;
    }

    private MapSqlParameterSource getParams(Outbox outbox) {
        return new MapSqlParameterSource()
                .addValue("eventType", outbox.getEventType().name())
                .addValue("payload", outbox.getPayload())
                .addValue("outboxStatus", outbox.getProcessingStatus().name())
                .addValue("createdAt", outbox.getCreatedAt())
                .addValue("processedAt", outbox.getProcessedAt());
    }
}
