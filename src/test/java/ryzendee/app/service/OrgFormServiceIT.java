package ryzendee.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.OrgForm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.testutils.FixtureUtil.orgFormFixture;

public class OrgFormServiceIT extends AbstractServiceIT {

    @Autowired
    private OrgFormService orgFormService;

    private OrgForm orgForm;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        orgForm = databaseUtil.insert(orgFormFixture());
    }

    @Test
    void saveOrUpdate_nonExistsOrgForm_shouldSaveInDb() {
        OrgFormSaveRequest request = OrgFormSaveRequest.builder()
                .name("New OrgForm")
                .build();

        OrgFormDetails saved = orgFormService.saveOrUpdate(request);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo(request.name());
    }

    @Test
    void saveOrUpdate_existsOrgForm_shouldUpdateInDb() {
        OrgFormSaveRequest request = OrgFormSaveRequest.builder()
                .id(orgForm.getId())
                .name("Updated OrgForm")
                .build();

        OrgFormDetails updated = orgFormService.saveOrUpdate(request);

        assertThat(updated).isNotNull();
        assertThat(updated.id()).isEqualTo(request.id());
        assertThat(updated.name()).isEqualTo(request.name());
    }

    @Test
    void getById_existsOrgForm_shouldReturnDetails() {
        OrgFormDetails details = orgFormService.getById(orgForm.getId());

        assertThat(details).isNotNull();
        assertThat(details.id()).isEqualTo(orgForm.getId());
        assertThat(details.name()).isEqualTo(orgForm.getName());
    }

    @Test
    void getById_nonExistsOrgForm_shouldThrow() {
        assertThatThrownBy(() -> orgFormService.getById(-1))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_existsOrgForm_shouldDelete() {
        orgFormService.deleteById(orgForm.getId());

        assertThat(databaseUtil.findOrgFormById(orgForm.getId())).isNull();
    }

    @Test
    void deleteById_nonExistsOrgForm_shouldThrow() {
        assertThatThrownBy(() -> orgFormService.deleteById(-1))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
