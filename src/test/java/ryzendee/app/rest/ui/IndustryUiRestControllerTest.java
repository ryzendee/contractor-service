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
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.rest.impl.ui.IndustryUiRestController;
import ryzendee.app.security.ContractorAccessRules;
import ryzendee.app.service.IndustryService;
import ryzendee.starter.jwt.config.JwtSecurityAutoConfiguration;
import ryzendee.starter.jwt.decoder.AuthRole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static ryzendee.app.testutils.FixtureUtil.authenticationFixture;
import static ryzendee.app.testutils.FixtureUtil.industrySaveRequestFixture;

@Import({
        SecurityConfiguration.class,
        JwtSecurityAutoConfiguration.class,
        ContractorAccessRules.class
})
@WebMvcTest(IndustryUiRestController.class)
public class IndustryUiRestControllerTest {

    private static final String BASE_URI = "/ui/industry";

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
    void getAll_authorizedRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .get("/all")
                .then()
                .status(HttpStatus.OK);

        verify(industryService).getAll();
    }


    @Test
    void getAll_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.ADMIN))
                .get("/all")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(industryService, never()).getAll();
    }

    @Test
    void getById_authorizedRole_statusOk() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .get("/{id}", industryId)
                .then()
                .status(HttpStatus.OK);

        verify(industryService).getById(industryId);
    }

    @Test
    void getById_unauthorizedRole_statusForbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.ADMIN))
                .get("/{id}", industryId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(industryService, never()).getById(anyInt());
    }

    @Test
    void saveOrUpdate_authorizedRole_statusOk() {
        IndustrySaveRequest saveRequest = industrySaveRequestFixture();

        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.OK);

        verify(industryService).saveOrUpdate(saveRequest);
    }

    @Test
    void saveOrUpdate_unauthorizedRole_statusForbidden() {
        IndustrySaveRequest saveRequest = industrySaveRequestFixture();

        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .body(saveRequest)
                .put("/save")
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(industryService, never()).saveOrUpdate(any(IndustrySaveRequest.class));
    }

    @Test
    void deleteById_authorizedRole_statusNoContent() {
        request.auth().authentication(authenticationFixture(AuthRole.SUPERUSER))
                .delete("/delete/{id}", industryId)
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(industryService).deleteById(industryId);
    }

    @Test
    void deleteById_unauthorizedRole_Forbidden() {
        request.auth().authentication(authenticationFixture(AuthRole.USER))
                .delete("/delete/{id}", industryId)
                .then()
                .status(HttpStatus.FORBIDDEN);

        verify(industryService, never()).deleteById(anyInt());
    }
}
