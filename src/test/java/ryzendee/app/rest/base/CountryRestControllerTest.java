package ryzendee.app.rest.base;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ryzendee.app.config.SecurityConfiguration;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.rest.impl.base.CountryRestController;
import ryzendee.app.service.CountryService;
import ryzendee.starter.jwt.config.JwtSecurityAutoConfiguration;

import static org.mockito.Mockito.*;
import static ryzendee.app.testutils.FixtureUtil.countrySaveRequestFixture;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CountryRestController.class)
public class CountryRestControllerTest {

    private static final String BASE_URI = "/country";

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
    void getAll_statusOk() {
        request.get("/all")
                .then()
                .status(HttpStatus.OK);

        verify(countryService).getAll();
    }

    @Test
    void getById_existsCountry_statusOk() {
        request.get("/{id}", countryId)
                .then()
                .status(HttpStatus.OK);

        verify(countryService).getById(countryId);
    }

    @Test
    void getById_nonExistsCountry_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(countryService).getById(countryId);

        request.get("/{id}", countryId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(countryService).getById(countryId);
    }

    @Test
    void saveOrUpdate_validRequest_statusOk() {
        CountrySaveRequest saveRequest = countrySaveRequestFixture();

        request.body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(countryService).saveOrUpdate(saveRequest);
    }

    @Test
    void deleteById_existsCountry_statusNoContent() {
        request.delete("/delete/{id}", countryId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(countryService).deleteById(countryId);
    }

    @Test
    void deleteById_nonExistsCountry_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(countryService).deleteById(countryId);

        request.delete("/delete/{id}", countryId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(countryService).deleteById(countryId);
    }
}
