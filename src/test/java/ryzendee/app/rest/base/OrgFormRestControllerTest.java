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
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.rest.impl.base.OrgFormRestController;
import ryzendee.app.service.OrgFormService;

import static org.mockito.Mockito.*;
import static ryzendee.app.testutils.FixtureUtil.orgFormSaveRequestFixture;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OrgFormRestController.class)
public class OrgFormRestControllerTest {

    private static final String BASE_URI = "/org-form";

    @MockitoBean
    private OrgFormService orgFormService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification request;
    private Integer orgFormId;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        request = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON);
        orgFormId = 1;
    }

    @Test
    void getAll_statusOk() {
        request.get("/all")
                .then()
                .status(HttpStatus.OK);

        verify(orgFormService).getAll();
    }

    @Test
    void getById_existsOrgForm_statusOk() {
        request.get("/{id}", orgFormId)
                .then()
                .status(HttpStatus.OK);

        verify(orgFormService).getById(orgFormId);
    }

    @Test
    void getById_nonExistsOrgForm_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(orgFormService).getById(orgFormId);

        request.get("/{id}", orgFormId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(orgFormService).getById(orgFormId);
    }

    @Test
    void saveOrUpdate_validRequest_statusOk() {
        OrgFormSaveRequest requestDto = orgFormSaveRequestFixture();

        request.body(requestDto)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(orgFormService).saveOrUpdate(requestDto);
    }

    @Test
    void deleteById_existsOrgForm_statusNoContent() {
        request.delete("/delete/{id}", orgFormId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(orgFormService).deleteById(orgFormId);
    }

    @Test
    void deleteById_nonExistsOrgForm_statusNotFound() {
        doThrow(ResourceNotFoundException.class)
                .when(orgFormService).deleteById(orgFormId);

        request.delete("/delete/{id}", orgFormId)
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(orgFormService).deleteById(orgFormId);
    }
}

