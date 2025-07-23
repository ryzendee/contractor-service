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

    private static final String BASE_SELECT_FOR_ACTIVE = """
            SELECT 
                o.id as org_form_id, 
                o.name as org_form_name, 
                o.is_active as org_form_is_active 
            FROM org_form as o
            WHERE o.is_active = true
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrgFormDetailsRowMapper orgFormDetailsRowMapper;

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
        String sql = BASE_SELECT_FOR_ACTIVE + " AND o.id = :id";

        List<OrgFormDetails> result = jdbcTemplate.query(sql, Map.of("id", id), orgFormDetailsRowMapper);
        return result.stream().findFirst();
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = """
                UPDATE org_form 
                SET is_active = false 
                WHERE id = :id
                """;
        int updated = jdbcTemplate.update(sql, Map.of("id", id));
        return updated > 0;
    }

    @Override
    public List<OrgFormDetails> findAll() {
        return jdbcTemplate.query(BASE_SELECT_FOR_ACTIVE, orgFormDetailsRowMapper);
    }

    private Number insert(OrgForm orgForm) {
        String sql = """
                    INSERT INTO org_form (name)
                    VALUES (:name)
                """;

        MapSqlParameterSource params = getParams(orgForm);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey();
    }

    private void update(OrgForm orgForm) {
        String sql = """
                    UPDATE org_form 
                    SET name = :name
                    WHERE id = :id
                """;

        MapSqlParameterSource params = getParams(orgForm);
        params.addValue("id", orgForm.getId());
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource getParams(OrgForm orgForm) {
        return new MapSqlParameterSource()
                .addValue("name", orgForm.getName());
    }
}
