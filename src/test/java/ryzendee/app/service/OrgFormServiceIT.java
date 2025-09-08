package ryzendee.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.model.OrgForm;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ryzendee.app.constants.CacheNameConstants.ORG_FORM_METADATA;
import static ryzendee.app.testutils.FixtureUtil.*;

public class OrgFormServiceIT extends AbstractServiceIT {

    private static final String CACHE_KEY = "all";

    @Autowired
    private OrgFormService orgFormService;

    private OrgForm orgForm;

    @BeforeEach
    void setUp() {
        databaseUtil.cleanDatabase();
        orgForm = databaseUtil.insert(orgFormFixture());
        cacheUtil.clearCache(ORG_FORM_METADATA);
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

    @Test
    void getAll_withoutCachedCountries_shouldPutCountriesInCache() {
        // Act
        List<OrgFormDetails> orgForms = orgFormService.getAll();
        orgForms = orgFormService.getAll();

        // Assert
        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(ORG_FORM_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNotNull();
        assertThat(cachedValue.get()).isInstanceOf(List.class);

        List<OrgFormDetails> cachedDetails = (List<OrgFormDetails>) cachedValue.get();
        assertThat(cachedDetails).containsExactlyElementsOf(orgForms);
    }

    @Test
    void saveOrUpdate_withCachedCountries_shouldClearCache() {
        cacheUtil.putInCache(ORG_FORM_METADATA, CACHE_KEY, List.of(orgFormDetailsFixture()));
        OrgFormSaveRequest request = orgFormSaveRequestFixture();

        orgFormService.saveOrUpdate(request);

        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(ORG_FORM_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNull();
    }

    @Test
    void deleteById_withCachedCountries_shouldClearCache() {
        cacheUtil.putInCache(ORG_FORM_METADATA, CACHE_KEY, List.of(orgFormDetailsFixture()));

        orgFormService.deleteById(orgForm.getId());

        Cache.ValueWrapper cachedValue = cacheUtil.getFromCache(ORG_FORM_METADATA, CACHE_KEY);
        assertThat(cachedValue).isNull();
    }
}
