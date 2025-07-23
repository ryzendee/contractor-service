package ryzendee.app.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.mapper.IndustryDetailsRowMapper;
import ryzendee.app.model.Industry;
import ryzendee.app.repository.IndustryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IndustryRepositoryImpl implements IndustryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final IndustryDetailsRowMapper industryDetailsRowMapper;

    private static final String BASE_SELECT_FOR_ACTIVE = """
                SELECT 
                    i.id as industry_id, 
                    i.name as industry_name, 
                    i.is_active as industry_is_active 
                FROM industry i
                WHERE i.is_active = true
            """;

    @Override
    public Industry save(Industry industry) {
        if (industry.getId() == null) {
            Number generatedId = insert(industry);
            industry.setId(generatedId.intValue());
        } else {
            update(industry);
        }

        return industry;
    }

    @Override
    public Optional<IndustryDetails> findById(Integer id) {
        String sql = BASE_SELECT_FOR_ACTIVE + " AND i.id = :id";
        return jdbcTemplate.query(sql, Map.of("id", id), industryDetailsRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = """
                UPDATE industry
                SET is_active = false 
                WHERE id = :id
                """;
        int updated = jdbcTemplate.update(sql, Map.of("id", id));
        return updated > 0;
    }

    @Override
    public List<IndustryDetails> findAll() {
        return jdbcTemplate.query(BASE_SELECT_FOR_ACTIVE, industryDetailsRowMapper);
    }

    private Number insert(Industry industry) {
        String sql = """
                    INSERT INTO industry (name)
                    VALUES (:name)
                """;

        MapSqlParameterSource params = getParams(industry);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey();
    }

    private void update(Industry industry) {
        String sql = """
                UPDATE industry 
                SET name = name 
                WHERE id = :id
                """;

        MapSqlParameterSource params = getParams(industry);
        params.addValue("id", industry.getId());
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource getParams(Industry industry) {
        return new MapSqlParameterSource()
                .addValue("name", industry.getName());
    }
}