package ryzendee.app.rest.ui;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ryzendee.app.config.SecurityConfiguration;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.rest.impl.ui.ContractorUiRestController;
import ryzendee.app.security.ContractorAccessRules;
import ryzendee.app.service.ContractorService;
import ryzendee.starter.jwt.auth.JwtAuthenticationToken;
import ryzendee.starter.jwt.config.JwtSecurityAutoConfiguration;
import ryzendee.starter.jwt.decoder.AuthRole;
import ryzendee.starter.jwt.decoder.JwtDecoder;
import ryzendee.starter.jwt.decoder.JwtPayload;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ryzendee.app.testutils.FixtureUtil.*;

@Import({
        SecurityConfiguration.class,
        JwtSecurityAutoConfiguration.class,
        ContractorAccessRules.class
})
@WebMvcTest(ContractorUiRestController.class)
public class ContractorUiRestControllerTest {

    private static final String BASE_URI = "/ui/contractor";
    private static final String ALLOWED_COUNTRY = "RUS";

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification request;
    @MockitoBean
    private ContractorService contractorService;

    private String contractorId;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        request = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON);
        contractorId = "id";
    }

    @Test
    void saveOrUpdateContractor_withUnauthorizedRole_statusForbidden() {
        ContractorSaveRequest requestDto = contractorSaveRequestBuilderFixture().build();

        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .body(requestDto)
                .put("/save")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(contractorService, never()).saveOrUpdateContractor(requestDto);
    }

    @Test
    void saveOrUpdateContractor_withSuperUserRole_statusOk() {
        ContractorSaveRequest requestDto = contractorSaveRequestBuilderFixture().build();

        Authentication authentication = authenticationFixture(AuthRole.SUPERUSER);
        request.auth().authentication(authentication)
                .body(requestDto)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(contractorService).saveOrUpdateContractor(requestDto);
    }

    @Test
    void getById_withSuperUserRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.CONTRACTOR_SUPERUSER))
                .get("/{id}", contractorId)
                .then()
                .status(HttpStatus.OK);

        verify(contractorService).getById(contractorId);
    }

    @Test
    void getById_withUnauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.CONTRACTOR_RUS))
                .get("/{id}", contractorId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(contractorService, never()).getById(any());
    }

    @Test
    void deleteById_withSuperUserRole_statusNoContent() {
        request.auth().authentication(authenticationFixture(AuthRole.CONTRACTOR_SUPERUSER))
                .delete("/delete/{id}", contractorId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(contractorService).deleteById(contractorId);
    }

    @Test
    void searchActiveContractors_withAllowedCountry_statusOk() {
        ContractorSearchFilter allowedFilter = ContractorSearchFilter.builder()
                .country(ALLOWED_COUNTRY)
                .build();

        request.auth().authentication(authenticationFixture(AuthRole.CONTRACTOR_RUS))
                .body(allowedFilter)
                .post("/search")
                .then()
                .status(HttpStatus.OK);

        verify(contractorService).searchActiveContractors(allowedFilter);
    }

    @Test
    void searchActiveContractors_withDisallowedCountry_statusForbidden() {
        ContractorSearchFilter disallowedFilter = ContractorSearchFilter.builder()
                .country("USA")
                .build();

        request.auth().authentication(authenticationFixture(AuthRole.CONTRACTOR_RUS))
                .body(disallowedFilter)
                .post("/search")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(contractorService, never()).searchActiveContractors(any());
    }
}
