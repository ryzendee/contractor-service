package ryzendee.app.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.mapper.ContractorDetailsRowMapper;
import ryzendee.app.model.*;
import ryzendee.app.repository.ContractorRepository;

import java.util.*;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ContractorRepositoryImpl implements ContractorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ContractorDetailsRowMapper contractorDetailsRowMapper;

    private static final String BASE_SELECT_FOR_ACTIVE_WITH_JOINS = """
        SELECT 
            c.id AS contractor_id,
            c.parent_id AS contractor_parent_id,
            c.name AS contractor_name,
            c.name_full AS contractor_name_full,
            c.inn AS contractor_inn,
            c.ogrn AS contractor_ogrn,
            co.id AS country_id,
            co.name AS country_name,
            i.id AS industry_id,
            i.name AS industry_name,
            o.id AS org_form_id,
            o.name AS org_form_name,
            c.create_date AS contractor_create_date,
            c.modify_date AS contractor_modify_date,
            c.create_user_id AS contractor_create_user_id,
            c.modify_user_id AS contractor_modify_user_id,
            c.is_active AS contractor_is_active
        FROM contractor c
        LEFT JOIN country co ON c.country = co.id
        LEFT JOIN industry i ON c.industry = i.id
        LEFT JOIN org_form o ON c.org_form = o.id
        WHERE c.is_active = true
        """;

    private static final String SQL_INSERT_OR_UPDATE = """
        INSERT INTO contractor (id, parent_id, name, name_full, inn, ogrn,
                                country, industry, org_form,
                                create_user_id, modify_user_id)
        VALUES (:id, :parentId, :name, :nameFull, :inn, :ogrn,
                :country, :industry, :orgForm,
                :createUserId, :modifyUserId)
        ON CONFLICT (id) DO UPDATE SET
            parent_id = EXCLUDED.parent_id,
            name = EXCLUDED.name,
            name_full = EXCLUDED.name_full,
            inn = EXCLUDED.inn,
            ogrn = EXCLUDED.ogrn,
            country = EXCLUDED.country,
            industry = EXCLUDED.industry,
            org_form = EXCLUDED.org_form,
            modify_date = now(),
            modify_user_id = EXCLUDED.modify_user_id,
            is_active = EXCLUDED.is_active
        """;

    private static final String SQL_DELETE_BY_ID = """
        UPDATE contractor
        SET is_active = false,
            modify_date = now()
        WHERE id = :id
        """;

    private static final String SQL_FIND_BY_ID = BASE_SELECT_FOR_ACTIVE_WITH_JOINS + " AND c.id = :id";

    private static final String SQL_ORDER_LIMIT_OFFSET = " ORDER BY c.id LIMIT :limit OFFSET :offset";

    @Override
    public Contractor save(Contractor c) {
        jdbcTemplate.update(SQL_INSERT_OR_UPDATE, contractorParams(c));
        return c;
    }

    @Override
    public Optional<ContractorDetails> findDetailsById(String id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, Map.of("id", id), contractorDetailsRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public boolean deleteById(String id) {
        int updated = jdbcTemplate.update(SQL_DELETE_BY_ID, Map.of("id", id));
        return updated > 0;
    }

    @Override
    public List<ContractorDetails> findAllDetailsByFilter(ContractorSearchFilter filter) {
        StringBuilder sql = new StringBuilder(BASE_SELECT_FOR_ACTIVE_WITH_JOINS);
        Map<String, Object> params = buildFilterParams(filter, sql);
        sql.append(SQL_ORDER_LIMIT_OFFSET);
        params.put("limit", filter.size());
        params.put("offset", filter.page() * filter.size());
        return jdbcTemplate.query(sql.toString(), params, contractorDetailsRowMapper);
    }

    private Map<String, Object> contractorParams(Contractor c) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", c.getId());
        params.put("parentId", c.getParentId());
        params.put("name", c.getName());
        params.put("nameFull", c.getNameFull());
        params.put("inn", c.getInn());
        params.put("ogrn", c.getOgrn());
        params.put("country", c.getCountry());
        params.put("industry", c.getIndustry());
        params.put("orgForm", c.getOrgForm());
        params.put("createUserId", c.getCreateUserId());
        params.put("modifyUserId", c.getModifyUserId());
        return params;
    }

    private Map<String, Object> buildFilterParams(ContractorSearchFilter filter, StringBuilder sql) {
        Map<String, Object> params = new HashMap<>();

        if (hasText(filter.contractorId())) {
            sql.append(" AND c.id = :contractorId");
            params.put("contractorId", filter.contractorId());
        }

        if (hasText(filter.parentId())) {
            sql.append(" AND c.parent_id = :parentId");
            params.put("parentId", filter.parentId());
        }

        if (hasText(filter.contractorSearch())) {
            sql.append("""
                        AND (
                            c.name ILIKE :search OR
                            c.name_full ILIKE :search OR
                            c.inn ILIKE :search OR
                            c.ogrn ILIKE :search
                        )
                    """);
            params.put("search", "%" + filter.contractorSearch() + "%");
        }

        if (hasText(filter.country())) {
            sql.append(" AND co.name ILIKE :country");
            params.put("country", "%" + filter.country() + "%");
        }

        if (filter.industry() != null) {
            sql.append(" AND c.industry = :industry");
            params.put("industry", filter.industry());
        }

        if (hasText(filter.orgForm())) {
            sql.append(" AND o.name ILIKE :orgForm");
            params.put("orgForm", "%" + filter.orgForm() + "%");
        }

        return params;
    }
}
