package ryzendee.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.Industry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.testutils.FixtureUtil.industryFixture;

public class IndustryServiceIT extends AbstractServiceIT {

    @Autowired
    private IndustryService industryService;

    private Industry industry;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        industry = databaseUtil.insert(industryFixture());
    }

    @Test
    void saveOrUpdate_nonExistsIndustry_shouldSaveInDb() {
        IndustrySaveRequest request = IndustrySaveRequest.builder()
                .name("New Industry")
                .build();

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
}

