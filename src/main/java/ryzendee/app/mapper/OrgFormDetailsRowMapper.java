package ryzendee.app.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ryzendee.app.dto.orgform.OrgFormDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrgFormDetailsRowMapper implements RowMapper<OrgFormDetails> {

    private static final String PREFIX = "org_form_";

    @Override
    public OrgFormDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = (Integer) rs.getObject(PREFIX + "id");

        if (id == null) {
            return null;
        }

        return new OrgFormDetails(
                id,
                rs.getString(PREFIX + "name")
        );
    }

}
