package ryzendee.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.Industry;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.constants.CacheNameConstants.INDUSTRY_METADATA;
import static ryzendee.app.testutils.FixtureUtil.*;

public class IndustryServiceIT extends AbstractServiceIT {

    private static final String CACHE_KEY = "all";

    @Autowired
    private IndustryService industryService;

    private Industry industry;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        industry = databaseUtil.insert(industryFixture());
        cacheUtil.clearCache(INDUSTRY_METADATA);
    }

    @Test
    void saveOrUpdate_nonExistsIndustry_shouldSaveInDb() {
        IndustrySaveRequest request = industrySaveRequestFixture();

        IndustryDetails saved = industryService.saveOrUpdate(request);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo(request.name());
    }

    @Test
    void saveOrUpdate_existsIndustry_shouldUpdateInDb() {
        IndustrySaveRequest request = IndustrySaveRequest.builder()
                .id(industry.getId())
                .name("Updated Industry Name")
                .build();

        IndustryDetails updated = industryService.saveOrUpdate(request);

        assertThat(updated).isNotNull();
        assertThat(updated.id()).isEqualTo(request.id());
        assertThat(updated.name()).isEqualTo(request.name());
    }

    @Test
    void getById_existsIndustry_shouldReturnIndustryDetails() {
        IndustryDetails details = industryService.getById(industry.getId());

        assertThat(details).isNotNull();
        assertThat(details.id()).isEqualTo(industry.getId());
        assertThat(details.name()).isEqualTo(industry.getName());
    }

    @Test
    void getById_nonExistsIndustry_shouldThrowResourceNotFound() {
        assertThatThrownBy(() -> industryService.getById(-1))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_existsIndustry_shouldDeleteIndustry() {
        industryService.deleteById(industry.getId());

        assertThat(databaseUtil.findIndustryById(industry.getId())).isNull();
    }

    @Test
    void deleteById_nonExistsIndustry_shouldThrowResourceNotFound() {
        assertThatThrownBy(() -> industryService.deleteById(-1))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getAll_withoutCachedCountries_shouldPutCountriesInCache() {
        // Act
        List<IndustryDetails> industries = industryService.getAll();
        industries = industryService.getAll();

        // Assert
        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(INDUSTRY_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNotNull();
        assertThat(cachedValue.get()).isInstanceOf(List.class);

        List<IndustryDetails> cachedDetails = (List<IndustryDetails>) cachedValue.get();
        assertThat(cachedDetails).containsExactlyElementsOf(industries);
    }

    @Test
    void saveOrUpdate_withCachedCountries_shouldClearCache() {
        cacheUtil.putInCache(INDUSTRY_METADATA, CACHE_KEY, List.of(industryDetailsFixture()));
        IndustrySaveRequest request = industrySaveRequestFixture();

        industryService.saveOrUpdate(request);

        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(INDUSTRY_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNull();
    }

    @Test
    void deleteById_withCachedCountries_shouldClearCache() {
        cacheUtil.putInCache(INDUSTRY_METADATA, CACHE_KEY, List.of(industryDetailsFixture()));

        industryService.deleteById(industry.getId());

        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(INDUSTRY_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNull();
    }
}

