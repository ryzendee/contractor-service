package ryzendee.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.Country;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.constants.CacheNameConstants.COUNTRY_METADATA;
import static ryzendee.app.testutils.FixtureUtil.*;

public class CountryServiceIT extends AbstractServiceIT {

    private static final String CACHE_KEY = "all";

    @Autowired
    private CountryService countryService;

    private Country country;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        cacheUtil.clearCache(COUNTRY_METADATA);
        country = databaseUtil.insert(countryFixture());
    }

    @Test
    void saveOrUpdate_nonExistsCountry_shouldSaveInDb() {
        CountrySaveRequest request = countrySaveRequestFixture();

        CountryDetails saved = countryService.saveOrUpdate(request);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isEqualTo(request.id());
        assertThat(saved.name()).isEqualTo(request.name());
    }

    @Test
    void saveOrUpdate_existsCountry_shouldUpdateInDb() {
        CountrySaveRequest request = CountrySaveRequest.builder()
                .id(country.getId())
                .name("Updated Country Name")
                .build();

        CountryDetails updated = countryService.saveOrUpdate(request);

        assertThat(updated).isNotNull();
        assertThat(updated.id()).isEqualTo(request.id());
        assertThat(updated.name()).isEqualTo(request.name());
    }

    @Test
    void getById_existsCountry_shouldReturnCountryDetails() {
        CountryDetails details = countryService.getById(country.getId());

        assertThat(details).isNotNull();
        assertThat(details.id()).isEqualTo(country.getId());
        assertThat(details.name()).isEqualTo(country.getName());
    }

    @Test
    void getById_nonExistsCountry_shouldThrowResourceNotFound() {
        assertThatThrownBy(() -> countryService.getById("dummy"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_existsCountry_shouldDeleteCountry() {
        countryService.deleteById(country.getId());

        assertThat(databaseUtil.findCountryById(country.getId())).isNull();
    }

    @Test
    void deleteById_nonExistsCountry_shouldThrowResourceNotFound() {
        assertThatThrownBy(() -> countryService.deleteById("dummy"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getAll_withoutCachedCountries_shouldPutCountriesInCache() {
        // Act
        List<CountryDetails> countries = countryService.getAll();
        countries = countryService.getAll();

        // Assert
        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(COUNTRY_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNotNull();
        assertThat(cachedValue.get()).isInstanceOf(List.class);

        List<CountryDetails> cachedDetails = (List<CountryDetails>) cachedValue.get();
        assertThat(cachedDetails).containsExactlyElementsOf(countries);
    }

    @Test
    void saveOrUpdate_withCachedCountries_shouldClearCache() {
        cacheUtil.putInCache(COUNTRY_METADATA, CACHE_KEY, List.of(countryDetailsFixture()));
        CountrySaveRequest request = countrySaveRequestFixture();

        countryService.saveOrUpdate(request);

        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(COUNTRY_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNull();
    }

    @Test
    void deleteById_withCachedCountries_shouldClearCache() {
        cacheUtil.putInCache(COUNTRY_METADATA, CACHE_KEY, List.of(countryDetailsFixture()));

        countryService.deleteById(country.getId());

        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(COUNTRY_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNull();
    }
}
