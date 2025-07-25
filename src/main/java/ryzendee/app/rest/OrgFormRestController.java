package ryzendee.app.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.rest.api.OrgFormApi;
import ryzendee.app.service.OrgFormService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrgFormRestController implements OrgFormApi {

    private final OrgFormService orgFormService;

    @Override
    public List<OrgFormDetails> getAll() {
        return orgFormService.getAll();
    }

    @Override
    public OrgFormDetails getById(Integer id) {
        return orgFormService.getById(id);
    }

    @Override
    public OrgFormDetails saveOrUpdate(OrgFormSaveRequest request) {
        return orgFormService.saveOrUpdate(request);
    }

    @Override
    public void deleteById(Integer id) {
        orgFormService.deleteById(id);
    }
}
