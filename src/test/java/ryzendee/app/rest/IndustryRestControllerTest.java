package ryzendee.app.rest;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.service.IndustryService;

import static org.mockito.Mockito.*;
import static ryzendee.app.testutils.FixtureUtil.industrySaveRequestFixture;

@WebMvcTest(IndustryRestController.class)
public class IndustryRestControllerTest {

    private static final String BASE_URI = "/industry";

    @MockitoBean
    private IndustryService industryService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification request;
    private Integer industryId;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        request = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON);
        industryId = 1;
    }

    @Test
    void getAll_statusOk() {
        request.get("/all")
                .then()
                .status(HttpStatus.OK);

        verify(industryService).getAll();
    }

    @Test
    void getById_existsIndustry_statusOk() {
        request.get("/{id}", industryId)
                .then()
                .status(HttpStatus.OK);

        verify(industryService).getById(industryId);
    }

    @Test
    void getById_nonExistsIndustry_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(industryService).getById(industryId);

        request.get("/{id}", industryId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(industryService).getById(industryId);
    }

    @Test
    void saveOrUpdate_validRequest_statusOk() {
        IndustrySaveRequest requestDto = industrySaveRequestFixture();

        request.body(requestDto)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(industryService).saveOrUpdate(requestDto);
    }

    @Test
    void deleteById_existsIndustry_statusNoContent() {
        request.delete("/delete/{id}", industryId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(industryService).deleteById(industryId);
    }

    @Test
    void deleteById_nonExistsIndustry_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(industryService).deleteById(industryId);

        request.delete("/delete/{id}", industryId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(industryService).deleteById(industryId);
    }
}
