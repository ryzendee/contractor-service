package ryzendee.app.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ryzendee.app.dto.country.CountryDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CountryDetailsRowMapper implements RowMapper<CountryDetails> {

    private static final String PREFIX = "country_";

    @Override
    public CountryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString(PREFIX + "id");

        if (id == null) {
            return null;
        }

        return new CountryDetails(
                id,
                rs.getString(PREFIX + "name")
        );
    }

}
