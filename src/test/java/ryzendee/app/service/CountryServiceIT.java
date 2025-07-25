package ryzendee.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.Country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.testutils.FixtureUtil.countryFixture;

public class CountryServiceIT extends AbstractServiceIT {

    @Autowired
    private CountryService countryService;

    private Country country;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        country = databaseUtil.insert(countryFixture());
    }

    @Test
    void saveOrUpdate_nonExistsCountry_shouldSaveInDb() {
        CountrySaveRequest request = CountrySaveRequest.builder()
                .id("new-country-id")
                .name("New Country")
                .build();

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

}
