package ryzendee.app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ryzendee.app.dto.contractor.ContractorDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class ContractorDetailsRowMapper implements RowMapper<ContractorDetails> {

    private static final String PREFIX = "contractor_";

    private final CountryDetailsRowMapper countryDetailsRowMapper;
    private final IndustryDetailsRowMapper industryDetailsRowMapper;
    private final OrgFormDetailsRowMapper orgFormDetailsRowMapper;

    @Override
    public ContractorDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ContractorDetails(
                rs.getString(PREFIX + "id"),
                rs.getString(PREFIX + "parent_id"),
                rs.getString(PREFIX + "name"),
                rs.getString(PREFIX + "name_full"),
                rs.getString(PREFIX + "inn"),
                rs.getString(PREFIX + "ogrn"),
                countryDetailsRowMapper.mapRow(rs, rowNum),
                industryDetailsRowMapper.mapRow(rs, rowNum),
                orgFormDetailsRowMapper.mapRow(rs, rowNum)
        );
    }
}
