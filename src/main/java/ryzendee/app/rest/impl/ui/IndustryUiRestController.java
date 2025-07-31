package ryzendee.app.rest.impl.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.rest.api.ui.IndustryUiApi;
import ryzendee.app.service.IndustryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IndustryUiRestController implements IndustryUiApi {

    private final IndustryService industryService;

    @PreAuthorize("@contractorAccessRules.canReadReferenceInfo(authentication)")
    @Override
    public List<IndustryDetails> getAll() {
        return industryService.getAll();
    }

    @PreAuthorize("@contractorAccessRules.canReadReferenceInfo(authentication)")
    @Override
    public IndustryDetails getById(Integer id) {
        return industryService.getById(id);
    }

    @PreAuthorize("@contractorAccessRules.canEditReferenceInfo(authentication)")
    @Override
    public IndustryDetails saveOrUpdate(IndustrySaveRequest request) {
        return industryService.saveOrUpdate(request);
    }

    @PreAuthorize("@contractorAccessRules.canEditReferenceInfo(authentication)")
    @Override
    public void deleteById(Integer id) {
        industryService.deleteById(id);
    }
}