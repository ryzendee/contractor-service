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
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.rest.impl.ui.OrgFormUiRestController;
import ryzendee.app.security.ContractorAccessRules;
import ryzendee.app.service.OrgFormService;
import ryzendee.starter.jwt.config.JwtSecurityAutoConfiguration;
import ryzendee.starter.jwt.decoder.AuthRole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static ryzendee.app.testutils.FixtureUtil.authenticationFixture;
import static ryzendee.app.testutils.FixtureUtil.orgFormSaveRequestFixture;

@Import({
        SecurityConfiguration.class,
        JwtSecurityAutoConfiguration.class,
        ContractorAccessRules.class
})
@WebMvcTest(OrgFormUiRestController.class)
public class OrgFormUiRestControllerTest {


    private static final String BASE_URI = "/ui/org-form";

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
    void getAll_authorizedRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .get("/all")
                .then()
                .status(HttpStatus.OK);

        verify(orgFormService).getAll();
    }


    @Test
    void getAll_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.ADMIN))
                .get("/all")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(orgFormService, never()).getAll();
    }

    @Test
    void getById_authorizedRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .get("/{id}", orgFormId)
                .then()
                .status(HttpStatus.OK);

        verify(orgFormService).getById(orgFormId);
    }

    @Test
    void getById_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.ADMIN))
                .get("/{id}", orgFormId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(orgFormService, never()).getById(anyInt());
    }

    @Test
    void saveOrUpdate_authorizedRole_statusOk() {
        OrgFormSaveRequest saveRequest = orgFormSaveRequestFixture();

        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(orgFormService).saveOrUpdate(saveRequest);
    }

    @Test
    void saveOrUpdate_unauthorizedRole_statusForbidden() {
        OrgFormSaveRequest saveRequest = orgFormSaveRequestFixture();

        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(orgFormService, never()).saveOrUpdate(any(OrgFormSaveRequest.class));
    }

    @Test
    void deleteById_authorizedRole_statusNoContent() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .delete("/delete/{id}", orgFormId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(orgFormService).deleteById(orgFormId);
    }

    @Test
    void deleteById_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .delete("/delete/{id}", orgFormId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(orgFormService, never()).deleteById(anyInt());
    }
}
