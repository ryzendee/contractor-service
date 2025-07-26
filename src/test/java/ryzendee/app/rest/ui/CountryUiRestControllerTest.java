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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ryzendee.app.config.SecurityConfiguration;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.rest.impl.ui.CountryUiRestController;
import ryzendee.app.security.ContractorAccessRules;
import ryzendee.app.service.CountryService;
import ryzendee.starter.jwt.config.JwtSecurityAutoConfiguration;
import ryzendee.starter.jwt.decoder.AuthRole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static ryzendee.app.testutils.FixtureUtil.authenticationFixture;
import static ryzendee.app.testutils.FixtureUtil.countrySaveRequestFixture;

@Import({
        SecurityConfiguration.class,
        JwtSecurityAutoConfiguration.class,
        ContractorAccessRules.class
})
@WebMvcTest(CountryUiRestController.class)
public class CountryUiRestControllerTest {

    private static final String BASE_URI = "/ui/country";

    @MockitoBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification request;
    private String countryId;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        request = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON);
        countryId = "id";
    }

    @Test
    void getAll_authorizedRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .get("/all")
                .then()
                .status(HttpStatus.OK);

        verify(countryService).getAll();
    }


    @Test
    void getAll_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.ADMIN))
                .get("/all")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(countryService, never()).getAll();
    }

    @Test
    void getById_authorizedRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .get("/{id}", countryId)
                .then()
                .status(HttpStatus.OK);

        verify(countryService).getById(countryId);
    }

    @Test
    void getById_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.ADMIN))
                .get("/{id}", countryId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(countryService, never()).getById(anyString());
    }

    @Test
    void saveOrUpdate_authorizedRole_statusOk() {
        CountrySaveRequest saveRequest = countrySaveRequestFixture();

        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(countryService).saveOrUpdate(saveRequest);
    }

    @Test
    void saveOrUpdate_unauthorizedRole_statusForbidden() {
        CountrySaveRequest saveRequest = countrySaveRequestFixture();

        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(countryService, never()).saveOrUpdate(any(CountrySaveRequest.class));
    }

    @Test
    void deleteById_authorizedRole_statusNoContent() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .delete("/delete/{id}", countryId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(countryService).deleteById(countryId);
    }

    @Test
    void deleteById_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .delete("/delete/{id}", countryId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(countryService, never()).deleteById(anyString());
    }
}
