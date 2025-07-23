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

    @Override
    public Country save(Country country) {
        String sql = """
                    INSERT INTO country (id, name)
                    VALUES (:id, :name)
                    ON CONFLICT (id) DO UPDATE SET
                        name = EXCLUDED.name
                """;

        jdbcTemplate.update(sql, Map.of(
                "id", country.getId(),
                "name", country.getName()
        ));

        return country;
    }

    @Override
    public Optional<CountryDetails> findById(String id) {
        String sql = BASE_SELECT_FOR_ACTIVE + " AND c.id = :id";
        return jdbcTemplate.query(sql, Map.of("id", id), countryDetailsRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public boolean deleteById(String id) {
        String sql = """
                UPDATE country 
                SET is_active = false 
                WHERE id = :id
                """;
        int updated = jdbcTemplate.update(sql, Map.of("id", id));
        return updated > 0;
    }

    @Override
    public List<CountryDetails> findAll() {
        return jdbcTemplate.query(BASE_SELECT_FOR_ACTIVE, countryDetailsRowMapper);
    }

}
