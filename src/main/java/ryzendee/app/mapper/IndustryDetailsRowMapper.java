package ryzendee.app.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ryzendee.app.dto.industry.IndustryDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class IndustryDetailsRowMapper implements RowMapper<IndustryDetails> {

    private static final String PREFIX = "industry_";

    @Override
    public IndustryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = (Integer) rs.getObject(PREFIX + "id");

        if (id == null) {
            return null;
        }

        return new IndustryDetails(
                id,
                rs.getString(PREFIX + "name")
        );
    }

}
