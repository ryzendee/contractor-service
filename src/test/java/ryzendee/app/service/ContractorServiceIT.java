package ryzendee.app.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.Contractor;
import ryzendee.app.model.Country;
import ryzendee.app.model.Industry;
import ryzendee.app.model.OrgForm;
import ryzendee.app.model.Outbox;
import ryzendee.app.repository.ContractorRepository;
import ryzendee.app.testutils.DatabaseUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.testutils.FixtureUtil.*;

public class ContractorServiceIT extends AbstractServiceIT {

    @Autowired
    private ContractorService service;

    @Autowired
    private ContractorRepository contractorRepository;

    private Contractor contractor;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        contractor = insertTestData();
    }

    @Test
    void saveOrUpdate_nonExistsContactor_shouldSaveInDb() {
        ContractorSaveRequest request = contractorSaveRequestBuilderFixture()
                .id("test")
                .country(contractor.getCountry())
                .orgForm(contractor.getOrgForm())
                .industry(contractor.getIndustry())
                .build();

        service.saveOrUpdateContractor(request);

        ContractorDetails saved = databaseUtil.findContractorById(request.id());
        assertThat(saved).isNotNull();
        assertContractorMatchesRequest(saved, request);
        assertThatOutboxSaved();
    }

    @Test
    void saveOrUpdate_existsContactor_shouldUpdateInDb() {
        ContractorSaveRequest request = contractorSaveRequestBuilderFixture()
                .id(contractor.getId())
                .name("updated-name")
                .country(contractor.getCountry())
                .orgForm(contractor.getOrgForm())
                .industry(contractor.getIndustry())
                .build();

        service.saveOrUpdateContractor(request);

        ContractorDetails updated = databaseUtil.findContractorById(request.id());
        assertThat(updated).isNotNull();
        assertContractorMatchesRequest(updated, request);
        assertThatOutboxSaved();
    }

    @Test
    void deleteById_existsContractor_shouldReturnNothing() {
        service.deleteById(contractor.getId());

        ContractorDetails deleted = databaseUtil.findContractorById(contractor.getId());
        assertThat(deleted).isNull();
    }

    @Test
    void deleteById_nonExistsContractor_shouldThrowResourceNotFoundEx() {
        assertThatThrownBy(() -> service.deleteById("dummy"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById_existsContractor_shouldReturnContractorInfo() {
        assertThat(service.getById(contractor.getId()))
                .isNotNull();
    }

    @Test
    void getById_nonExistsContractor_shouldThrowResourceNotFoundEx() {
        assertThatThrownBy(() -> service.getById("dummy"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private void assertContractorMatchesRequest(ContractorDetails actual, ContractorSaveRequest expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.parentId()).isEqualTo(expected.parentId());
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.nameFull()).isEqualTo(expected.nameFull());
        assertThat(actual.inn()).isEqualTo(expected.inn());
        assertThat(actual.ogrn()).isEqualTo(expected.ogrn());
        assertThat(actual.country().id()).isEqualTo(expected.country());
        assertThat(actual.industry().id()).isEqualTo(expected.industry());
        assertThat(actual.orgForm().id()).isEqualTo(expected.orgForm());
    }
    
    private void assertThatOutboxSaved() {
        int limit = 50;
        Outbox outbox = databaseUtil.findAllPendingOutboxes(limit).getFirst();
        assertThat(outbox).isNotNull();
    }

    private Contractor insertTestData() {
        OrgForm orgForm = orgFormFixture();
        Industry industry = industryFixture();
        Country country = countryFixture();

        databaseUtil.insert(orgForm);
        databaseUtil.insert(industry);
        databaseUtil.insert(country);

        Contractor contractor = contractorFixture();
        contractor.setCountry(country.getId());
        contractor.setIndustry(industry.getId());
        contractor.setOrgForm(orgForm.getId());

        return contractorRepository.save(contractor);
    }
}
