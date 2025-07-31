package ryzendee.app.rest.impl.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.rest.api.ui.OrgFormUiApi;
import ryzendee.app.service.OrgFormService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrgFormUiRestController implements OrgFormUiApi {

    private final OrgFormService orgFormService;

    @PreAuthorize("@contractorAccessRules.canReadReferenceInfo(authentication)")
    @Override
    public List<OrgFormDetails> getAll() {
        return orgFormService.getAll();
    }

    @PreAuthorize("@contractorAccessRules.canReadReferenceInfo(authentication)")
    @Override
    public OrgFormDetails getById(Integer id) {
        return orgFormService.getById(id);
    }

    @PreAuthorize("@contractorAccessRules.canEditReferenceInfo(authentication)")
    @Override
    public OrgFormDetails saveOrUpdate(OrgFormSaveRequest request) {
        return orgFormService.saveOrUpdate(request);
    }

    @PreAuthorize("@contractorAccessRules.canEditReferenceInfo(authentication)")
    @Override
    public void deleteById(Integer id) {
        orgFormService.deleteById(id);
    }
}

