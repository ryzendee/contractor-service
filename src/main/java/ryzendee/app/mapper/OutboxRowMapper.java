package ryzendee.app.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ryzendee.app.common.enums.EventType;
import ryzendee.app.common.enums.ProcessingStatus;
import ryzendee.app.model.Outbox;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OutboxRowMapper implements RowMapper<Outbox> {

    @Override
    public Outbox mapRow(ResultSet rs, int rowNum) throws SQLException {
        Outbox outbox = new Outbox();
        outbox.setId(rs.getLong("id"));
        outbox.setEventType(EventType.valueOf(rs.getString("event_type")));
        outbox.setPayload(rs.getString("payload"));

        String statusStr = rs.getString("outbox_status");
        if (statusStr != null) {
            outbox.setProcessingStatus(ProcessingStatus.valueOf(statusStr));
        }

        outbox.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        outbox.setProcessedAt(rs.getTimestamp("processed_at").toLocalDateTime());

        return outbox;
    }

}
