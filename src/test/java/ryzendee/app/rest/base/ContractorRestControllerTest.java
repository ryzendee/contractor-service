package ryzendee.app.rest.base;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.rest.impl.base.ContractorRestController;
import ryzendee.app.service.impl.ContractorServiceImpl;

import static org.mockito.Mockito.*;
import static ryzendee.app.testutils.FixtureUtil.contractorSaveRequestBuilderFixture;
import static ryzendee.app.testutils.FixtureUtil.contractorSearchFilterFixture;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ContractorRestController.class)
public class ContractorRestControllerTest {

    private static final String BASE_URI = "/contractor";

    @MockitoBean
    private ContractorServiceImpl contractorService;
    @Autowired
    private MockMvc mockMvc;
    private MockMvcRequestSpecification request;
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
    void saveOrUpdateContractor_validRequest_statusOk() {
        ContractorSaveRequest contractorSaveRequest = contractorSaveRequestBuilderFixture().build();

        request.body(contractorSaveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(contractorService).saveOrUpdateContractor(contractorSaveRequest);
    }

    @Test
    void getById_existsContractor_statusOk() {
        request.get("/{id}", contractorId)
                .then()
                .status(HttpStatus.OK);

        verify(contractorService).getById(contractorId);
    }

    @Test
    void getById_nonExistsContractor_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(contractorService).getById(contractorId);

        request.get("/{id}", contractorId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(contractorService).getById(contractorId);
    }

    @Test
    void deleteById_existsContractor_statusOk() {
        request.delete("/delete/{id}", contractorId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(contractorService).deleteById(contractorId);
    }

    @Test
    void deleteById_nonExistsContractor_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(contractorService).deleteById(contractorId);

        request.delete("/delete/{id}", contractorId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(contractorService).deleteById(contractorId);
    }

    @Test
    void searchActiveContractors_validFilter_statusOk() {
        ContractorSearchFilter filterDto = contractorSearchFilterFixture();

        request.body(filterDto)
                .post("/search")
                .then()
                .status(HttpStatus.OK);

        verify(contractorService).searchActiveContractors(filterDto);
    }
}
