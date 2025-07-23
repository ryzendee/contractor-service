package ryzendee.app.testutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.model.Contractor;
import ryzendee.app.model.Country;
import ryzendee.app.model.Industry;
import ryzendee.app.model.OrgForm;
import ryzendee.app.repository.ContractorRepository;
import ryzendee.app.repository.CountryRepository;
import ryzendee.app.repository.IndustryRepository;
import ryzendee.app.repository.OrgFormRepository;

public class DatabaseUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private OrgFormRepository orgFormRepository;
    @Autowired
    private ContractorRepository contractorRepository;

    public Country insert(Country country) {
        return countryRepository.save(country);
    }

    public Industry insert(Industry industry) {
        return industryRepository.save(industry);
    }

    public OrgForm insert(OrgForm orgForm) {
        return orgFormRepository.save(orgForm);
    }

    public Contractor insert(Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    public CountryDetails findCountryById(String id) {
        return countryRepository.findById(id).orElse(null);
    }

    public IndustryDetails findIndustryById(Integer id) {
        return industryRepository.findById(id).orElse(null);
    }

    public OrgFormDetails findOrgFormById(Integer id) {
        return orgFormRepository.findById(id).orElse(null);
    }

    public ContractorDetails findContractorById(String id) {
        return contractorRepository.findDetailsById(id).orElse(null);
    }

    public void cleanDatabase() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "contractor",
                "org_form",
                "industry",
                "country"
        );
    }

    @TestConfiguration
    public static class Config {
        @Bean
        public DatabaseUtil databaseUtil() {
            return new DatabaseUtil();
        }
    }
}
