package ryzendee.app.testutils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.model.Contractor;
import ryzendee.app.model.Country;
import ryzendee.app.model.Industry;
import ryzendee.app.model.OrgForm;
import ryzendee.starter.jwt.auth.JwtAuthenticationToken;
import ryzendee.starter.jwt.decoder.AuthRole;

import java.util.List;

public class FixtureUtil {

    public static OrgFormSaveRequest orgFormSaveRequestFixture() {
        return new OrgFormSaveRequest(1, "name");
    }

    public static IndustrySaveRequest industrySaveRequestFixture() {
        return new IndustrySaveRequest(1, "name");
    }

    public static CountrySaveRequest countrySaveRequestFixture() {
        return new CountrySaveRequest("id", "name");
    }

    public static Contractor contractorFixture() {
        return Contractor.builder()
                .id("123456789012")
                .name("ООО Ромашка")
                .nameFull("Общество с ограниченной ответственностью Ромашка")
                .inn("1234567890")
                .ogrn("1234567890123")
                .isActive(true)
                .build();
    }

    public static Country countryFixture() {
        return Country.builder()
                .id("test")
                .name("test")
                .isActive(true)
                .build();
    }

    public static Industry industryFixture() {
        return Industry.builder()
                .name("test")
                .isActive(true)
                .build();
    }

    public static OrgForm orgFormFixture() {
        return OrgForm.builder()
                .name("test")
                .isActive(true)
                .build();
    }

    public static ContractorSaveRequest.ContractorSaveRequestBuilder contractorSaveRequestBuilderFixture() {
        return ContractorSaveRequest.builder()
                .id("123456789012")
                .name("ООО Ромашка")
                .nameFull("Общество с ограниченной ответственностью Ромашка")
                .inn("1234567890")
                .ogrn("1234567890123")
                .country("12312352")
                .industry(1)
                .orgForm(2);
    }

    public static ContractorSearchFilter contractorSearchFilterFixture() {
        return ContractorSearchFilter.builder()
                .contractorId("123456789012")
                .parentId("987654321098")
                .contractorSearch("ромашка")
                .country("RUS")
                .industry(1)
                .orgForm("ООО")
                .page(0)
                .size(10)
                .build();
    }

    public static Authentication authenticationFixture(AuthRole role) {
        return new JwtAuthenticationToken("user", "jwt-token", List.of(new SimpleGrantedAuthority("ROLE_" + role.name())));
    }

}
