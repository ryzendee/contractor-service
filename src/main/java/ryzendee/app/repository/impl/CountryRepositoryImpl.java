package ryzendee.app.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.mapper.CountryDetailsRowMapper;
import ryzendee.app.model.Country;
import ryzendee.app.repository.CountryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CountryDetailsRowMapper countryDetailsRowMapper;

    private static final String BASE_SELECT_FOR_ACTIVE = """
        SELECT 
            c.id as country_id, 
            c.name as country_name, 
            c.is_active as country_is_active 
        FROM country c
        WHERE c.is_active = true
        """;

    private static final String SQL_INSERT_OR_UPDATE = """
        INSERT INTO country (id, name)
        VALUES (:id, :name)
        ON CONFLICT (id) DO UPDATE SET
            name = EXCLUDED.name
        """;

    private static final String SQL_FIND_BY_ID = BASE_SELECT_FOR_ACTIVE + " AND c.id = :id";

    private static final String SQL_DELETE_BY_ID = """
        UPDATE country 
        SET is_active = false 
        WHERE id = :id
        """;

    @Override
    public Country save(Country country) {
        jdbcTemplate.update(SQL_INSERT_OR_UPDATE, Map.of(
                "id", country.getId(),
                "name", country.getName()
        ));
        return country;
    }

    @Override
    public Optional<CountryDetails> findById(String id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, Map.of("id", id), countryDetailsRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public boolean deleteById(String id) {
        int updated = jdbcTemplate.update(SQL_DELETE_BY_ID, Map.of("id", id));
        return updated > 0;
    }

    @Override
    public List<CountryDetails> findAll() {
        return jdbcTemplate.query(BASE_SELECT_FOR_ACTIVE, countryDetailsRowMapper);
    }
}
