package ryzendee.app.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.mapper.OrgFormDetailsRowMapper;
import ryzendee.app.model.OrgForm;
import ryzendee.app.repository.OrgFormRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrgFormRepositoryImpl implements OrgFormRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrgFormDetailsRowMapper orgFormDetailsRowMapper;

    private static final String BASE_SELECT_FOR_ACTIVE = """
            SELECT 
                o.id as org_form_id, 
                o.name as org_form_name, 
                o.is_active as org_form_is_active 
            FROM org_form as o
            WHERE o.is_active = true
            """;

    private static final String SQL_FIND_BY_ID = BASE_SELECT_FOR_ACTIVE + " AND o.id = :id";

    private static final String SQL_DELETE_BY_ID = """
            UPDATE org_form 
            SET is_active = false 
            WHERE id = :id
            """;

    private static final String SQL_INSERT = """
            INSERT INTO org_form (name)
            VALUES (:name)
            """;

    private static final String SQL_UPDATE = """
            UPDATE org_form 
            SET name = :name
            WHERE id = :id
            """;

    @Override
    public OrgForm save(OrgForm orgForm) {
        if (orgForm.getId() == null) {
            Number generatedId = insert(orgForm);
            orgForm.setId(generatedId.intValue());
        } else {
            update(orgForm);
        }

        return orgForm;
    }

    @Override
    public Optional<OrgFormDetails> findById(Integer id) {
        List<OrgFormDetails> result = jdbcTemplate.query(SQL_FIND_BY_ID, Map.of("id", id), orgFormDetailsRowMapper);
        return result.stream().findFirst();
    }

    @Override
    public boolean deleteById(Integer id) {
        int updated = jdbcTemplate.update(SQL_DELETE_BY_ID, Map.of("id", id));
        return updated > 0;
    }

    @Override
    public List<OrgFormDetails> findAll() {
        return jdbcTemplate.query(BASE_SELECT_FOR_ACTIVE, orgFormDetailsRowMapper);
    }

    private Number insert(OrgForm orgForm) {
        MapSqlParameterSource params = getParams(orgForm);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_INSERT, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey();
    }

    private void update(OrgForm orgForm) {
        MapSqlParameterSource params = getParams(orgForm);
        params.addValue("id", orgForm.getId());
        jdbcTemplate.update(SQL_UPDATE, params);
    }

    private MapSqlParameterSource getParams(OrgForm orgForm) {
        return new MapSqlParameterSource()
                .addValue("name", orgForm.getName());
    }
}
